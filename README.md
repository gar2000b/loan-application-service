# loan-application-service
Loan Application Service

docker network create -d bridge social-insurance  
docker network ls  

docker build -t gar2000b/loan-application-service .  
docker run -it -d -p 9080:9080 --network="social-insurance" --name loan-application-service gar2000b/loan-application-service  

All optional:

docker create -it gar2000b/loan-application-service bash  
docker ps -a  
docker start ####  
docker ps  
docker attach ####  
docker remove ####  
docker image rm gar2000b/loan-application-service  
docker exec -it loan-application-service sh  
docker login  
docker push gar2000b/loan-application-service  

For Docker Compose:

docker-compose up -d  
docker-compose down  
docker-compose start  
docker-compose stop  

For the whole social insurance workflow suite with Kubernetes

cd kubernetes/social-insurance-workflow-kubes  
kubectl create -f .  
kubectl port-forward loan-application 9080:9080

To remove all, execute the script:  
./remove-deployments-services.sh

To test:  
curl -i -H "Content-Type:application/json" -X POST --data '{"CustomerId":"1234","Location":"Toronto","AmountRequested":"1000.00","Denomination":"CAD","LoanPeriod":"2 Years"}' http://IP-ADDRESS-HERE:9080/application
