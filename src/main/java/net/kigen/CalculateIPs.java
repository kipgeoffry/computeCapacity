package net.kigen;

import org.apache.commons.net.util.SubnetUtils;

import java.util.HashMap;


public class CalculateIPs {
    private String cidrIpAddress;
    private String product;
    private String bandwidth;

    public String getCidrIpAddress() {
        return cidrIpAddress;
    }

    public void setCidrIpAddress(String cidrIpAddress) {
        this.cidrIpAddress = cidrIpAddress;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
    }

    //to calculate router IP used by npm bot
    public String getRouterIP(){
        SubnetUtils utils = new SubnetUtils(getCidrIpAddress());
        String netmask = utils.getInfo().getNetmask();
        try {
            String subnet30Netmask = "255.255.255.252";
            String subnet31Netmask = "255.255.255.254";
            if (netmask.equals(subnet30Netmask)){
                return utils.getInfo().getHighAddress();
            } else if (netmask.equals(subnet31Netmask)) {
                return utils.getInfo().getBroadcastAddress();
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return utils.getInfo().getAddress();
    }

    //get Allot IPs
    public HashMap<String, String> getAllotIps(){
        SubnetUtils utils = new SubnetUtils(getCidrIpAddress());
        HashMap<String, String> allotIPs = new HashMap<String, String>();
        String networkIP = utils.getInfo().getNetworkAddress();
        allotIPs.put("network",networkIP);
        String mask = utils.getInfo().getNetmask();
        allotIPs.put("subnet",mask);
        return allotIPs;
    }

    //to get NCE bandwidth
    public String  getNceBandwidth(){
        if (getProduct().contains("Connectivity For Business")) {
            return getBandwidth().replaceAll("(\\d+)\\s*(Mbps)", "$1 $2");
        } else if (getProduct().contains("IFB")) {
            return ("IFB " + getBandwidth().replaceAll("\\s+", ""));
        } else if (getProduct().contains("KIFARUNET")) {
            String[] bwParts = getBandwidth().split("\\s+");
            if (bwParts.length == 2) {
                return "KifaruNet " + bwParts[0];
            } else {
                System.out.println("Invalid input format");
                //Todo handle invalid error
            }
        }

        return getBandwidth();

    }

    //to get Allot capacities

    public String getAllotBandwidth(){
        String bandwidth = getBandwidth();
        if (getProduct().contains("Connectivity For Business")){
            return String.format("%s %s", getProduct(),getBandwidth());
        }else if (getProduct().contains("IFB")) {
            bandwidth = getBandwidth().replaceAll("\\s+", "");
            return String.format("Connectivity for Business %s", bandwidth);
        } else if (getProduct().contains("KIFARUNET")) {
            String[] parts = getBandwidth().split("\\s+");
            if(parts.length == 2){
                String bw = parts[0];
                return  ("KIFARUNET " + bw);
            } else {
                System.out.println("Invalid input format");
            }

        }
        return bandwidth;

    }
}