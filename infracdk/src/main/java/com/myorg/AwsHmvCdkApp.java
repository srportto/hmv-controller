package com.myorg;

import software.amazon.awscdk.App;

public class AwsHmvCdkApp {
    public static void main(final String[] args) {
        App app = new App();

        //Criação da Stack da VPC (virtual private cloud)
        VpcStack vpcStack = new VpcStack(app, "VpcHmvCdk");

        //Criação do Cluster ECS (Elastic Container Service)
        ClusterEcsStack clusterEcsStack = new ClusterEcsStack(app, "ClusterEcsHmvCdk", vpcStack.getVpc());
        clusterEcsStack.addDependency(vpcStack); //?dependencia do clusterStack para o vpcStack

        //Criação da Stack do Banco de dados relacional(RDS)
        RdsStack rdsStack = new RdsStack(app, "RdsHmvCdk", vpcStack.getVpc());
        rdsStack.addDependency(vpcStack);

        //Criação da stack do load balancer (Elastic Load Balancer) que carregara o cluster ECS com a aplicação java Spring Boot
        Service01Stack service01Stack = new Service01Stack(app, "ServiceHmvCdk", clusterEcsStack.getCluster());
        service01Stack.addDependency(clusterEcsStack);
        service01Stack.addDependency(rdsStack);

        app.synth();
    }
}

