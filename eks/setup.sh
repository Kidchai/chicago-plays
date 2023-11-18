#!/bin/sh

# Usage: DB_PASSWORD=somepassword ./setup.sh

REGION="us-east-2"

aws ecr create-repository --repository-name chicago-plays-frontend --region $REGION
aws ecr create-repository --repository-name chicago-plays-backend  --region $REGION

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

eksctl create cluster --name chicago-plays --region $REGION --nodegroup-name standard-workers --node-type t2.medium --nodes 3 --nodes-min 1 --nodes-max 4 --managed

aws eks --region $REGION update-kubeconfig --name chicago-plays

kubectl create secret generic chicago-plays-db-secret \
  --from-literal=SPRING_DATASOURCE_PASSWORD=$DB_PASSWORD \
  --from-literal=SPRING_DATASOURCE_URL="jdbc:postgresql://$DB_URL:5432/chicago-plays"

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
