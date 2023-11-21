package net.kigen;


import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        CalculateIPs output = new CalculateIPs();
        //Connectivity For Business,IFB or KIFARUNET
        output.setProduct("KIFARUNET");
        //for connectivity For Business, bandwidth from crm is in the format xxMbps
        //for IFB and KIFARUNET, bandwidth from crm is in the format xx Mbps
        output.setBandwidth("20 Mbps");
        output.setCidrIpAddress("172.18.1.254/30");

        System.out.println("NCE bandwidth: " + output.getNceBandwidth());
        System.out.println("Allot bandwidth: " + output.getAllotBandwidth());

        System.out.println("NPM bot Router IP: " + output.getRouterIP());

        System.out.println("Allot IPs: Network: " + output.getAllotIps().get("network") + " Subnet: " + output.getAllotIps().get("subnet") );

    }
}