#!/bin/sh

# Usage: DOMAIN=appdomain DB_PASSWORD=somepassword ./setup.sh
CLUSTER="chicago-plays"
REGION="us-east-2"

aws ecr create-repository --repository-name chicago-plays-frontend --region $REGION
aws ecr create-repository --repository-name chicago-plays-backend  --region $REGION

# TODO: create rds in the same VPC as eks cluster
aws rds create-db-instance \
  --db-instance-identifier chicago-plays-db \
  --db-instance-class db.t3.micro \
  --engine postgres \
  --allocated-storage 20 \
  --master-username postgres \
  --master-user-password $DB_PASSWORD \
  --no-publicly-accessible

# TODO:
# DB_URL=??? something.rds.amazonaws.com

eksctl create cluster --name $CLUSTER --region $REGION --nodegroup-name standard-workers --node-type t2.medium --nodes 3 --nodes-min 1 --nodes-max 4 --managed

aws eks --region $REGION update-kubeconfig --name $CLUSTER

POLICY_ARN=$(aws iam create-policy \
  --policy-name "AWSLoadBalancerControllerIAMPolicy" \
  --policy-document file://iam_policy.json \
  --query 'Policy.Arn' --output text)
echo "Created IAM policy: $POLICY_ARN"

SERVICE_ACCOUNT_NAME="aws-load-balancer-controller"

# Create an IAM role and associate it with the Kubernetes service account
eksctl create iamserviceaccount \
  --cluster=$CLUSTER \
  --namespace=kube-system \
  --name=$SERVICE_ACCOUNT_NAME \
  --attach-policy-arn=$POLICY_ARN \
  --override-existing-serviceaccounts \
  --approve
echo "Created IAM service account in Kubernetes"

helm repo add eks https://aws.github.io/eks-charts
helm repo update

helm install aws-load-balancer-controller eks/aws-load-balancer-controller \
  -n kube-system \
  --set clusterName=$CLUSTER \
  --set serviceAccount.create=false \
  --set serviceAccount.name=$SERVICE_ACCOUNT_NAME

echo "AWS Load Balancer Controller installation initiated"

kubectl create serviceaccount $SERVICE_ACCOUNT_NAME -n kube-system

# Verify the Installation
echo "Verifying the installation..."
kubectl get deployment -n kube-system aws-load-balancer-controller

kubectl create secret generic chicago-plays-db-secret \
  --from-literal=SPRING_DATASOURCE_PASSWORD=$DB_PASSWORD \
  --from-literal=SPRING_DATASOURCE_URL="jdbc:postgresql://$DB_URL:5432/chicago-plays"

DOMAIN_CERT_ARN=`aws acm request-certificate --domain-name $DOMAIN --validation-method DNS | grep CertificateArn | cut -d '"' -f 4`
echo "Generated $DOMAIN certificate: $DOMAIN_CERT_ARN"

CNAME_DATA=`aws acm describe-certificate --certificate-arn $DOMAIN_CERT_ARN --query 'Certificate.DomainValidationOptions[*].ResourceRecord'`
echo $CNAME_DATA

sed -e "s#DOMAIN_CERT_ARN#$DOMAIN_CERT_ARN#" ingress.yaml.template > ingress.yaml

for file in *.yaml; do
  kubectl apply -f $file
done

# Status:
# kubectl get deployments
# kubectl get pods
# kubectl get services

# Access:
# kubectl get svc frontend

# Get kubeconfig:
# cat ~/.kube/config | base64
