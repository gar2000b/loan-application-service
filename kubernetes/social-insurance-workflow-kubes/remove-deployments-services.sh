#!/bin/bash

# Remove Pods
kubectl delete pod customer
kubectl delete pod fraud-check
kubectl delete pod loan-application
kubectl delete pod social-insurance-verification
kubectl delete pod social-insurance-workflow

# Remove service account
kubectl delete serviceaccount customer
kubectl delete serviceaccount fraud-check
kubectl delete serviceaccount loan-application
kubectl delete serviceaccount social-insurance-verification
kubectl delete serviceaccount social-insurance-workflow

# Remove Services
kubectl delete service loan-application-service-load-balancer
kubectl delete service social-insurance-workflow-service-load-balancer
