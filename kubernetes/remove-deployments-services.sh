#!/bin/bash

# Remove Deployments
kubectl delete deployment customer-deployment
kubectl delete deployment fraud-check-deployment
kubectl delete deployment loan-application-deployment
kubectl delete deployment social-insurance-verification-deployment
kubectl delete deployment social-insurance-workflow-deployment

# Remove Services
kubectl delete service customer-service
kubectl delete service fraud-check-service
kubectl delete service loan-application-service
kubectl delete service social-insurance-verification-service
kubectl delete service social-insurance-workflow-service
