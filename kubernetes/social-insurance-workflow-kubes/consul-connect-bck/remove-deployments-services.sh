#!/bin/bash

# Remove Deployments
kubectl delete deployment customer
kubectl delete deployment fraud-check
kubectl delete deployment loan-application
kubectl delete deployment social-insurance-verification
kubectl delete deployment social-insurance-workflow

# Remove Services
kubectl delete service customer
kubectl delete service fraud-check
kubectl delete service loan-application
kubectl delete service social-insurance-verification
kubectl delete service social-insurance-workflow
