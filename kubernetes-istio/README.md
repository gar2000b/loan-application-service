Istio files relating to social insurance workflow services all go here.

Deployment Instructions on to minikube:

minikube start --driver=virtuabox

Download istio:  
curl -L https://istio.io/downloadIstio | sh -  
cd istio-1.5.0  
export PATH=$PWD/bin:$PATH  

As you have this project already checked out, cd into kubernetes-istio directory

Install istio:  
istioctl manifest apply --set profile=demo

Add default namespace label:  
kubectl label namespace default istio-injection=enabled

Deploy the apps one at a time (recommend a 10 sec gap once the pods are running):  
kubectl appy -f loan-application.yaml  
kubectl get all  
kubectl appy -f customer.yaml  
kubectl get all  
kubectl appy -f fraud-check.yaml  
kubectl get all  
kubectl appy -f social-insurance-verification.yaml  
kubectl get all  
kubectl appy -f social-insurance-workflow.yaml  
kubectl get all  

Deploy gateway:  
kubectl apply -f gateway/social-insurance-gateway.yaml
kubectl get gateway

Expose ingress IP and ports:  
export INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].nodePort}')  
export SECURE_INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="https")].nodePort}')  
echo $INGRESS_PORT  
echo $SECURE_INGRESS_PORT  
export INGRESS_HOST=$(minikube ip)  
echo $INGRESS_HOST  

Open a new terminal tab to start a minikube tunnel:  
minikube tunnel

Get gateway URL and hit it a few times with curl (substitute IP address and port for $GATEWAY_URL):  
export GATEWAY_URL=$INGRESS_HOST:$INGRESS_PORT  
echo http://$GATEWAY_URL/application  
curl -i -H "Content-Type:application/json" -X POST --data '{"CustomerId":"1234","Location":"Toronto","AmountRequested":"1000.00","Denomination":"CAD","LoanPeriod":"2 Years"}' http://192.168.99.108:30262/application

Open up the kiali dashboard:  
istioctl dashboard kiali  
Select graph on the LH side.  
Make sure namespace is set to default.

Most of this info is taken from: https://istio.io/docs/setup/getting-started/

