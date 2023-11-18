#!/bin/sh

# Usage: DB_PASSWORD=somepassword ./setup.sh

REGION="us-east-2"

aws ecr create-repository --repository-name chicago-plays --region $REGION

aws rds create-db-instance \
    --db-instance-identifier chicago-plays-db \
    --db-instance-class db.t3.micro \
    --engine postgres \
    --allocated-storage 20 \
    --master-username postgres \
    --master-user-password $DB_PASSWORD \
    --no-publicly-accessible

eksctl create cluster --name chicago-plays --region $REGION --nodegroup-name standard-workers --node-type t2.medium --nodes 3 --nodes-min 1 --nodes-max 4 --managed

aws eks --region $REGION update-kubeconfig --name chicago-plays

kubectl create secret generic chicago-plays-db-secret --from-literal=SPRING_DATASOURCE_PASSWORD=$DB_PASSWORD

kubectl apply -f frontend-service.yaml,frontend-deployment.yaml,backend-service.yaml,backend-deployment.yaml

# Status:
# kubectl get deployments
# kubectl get pods
# kubectl get services

# Access:
# kubectl get svc frontend.

# Get kubeconfig:
# cat ~/.kube/config | base64
