/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

/**
 *
 * @author evansleong
 */
public class PremiumMember extends Membership{
    private static double premiumRate = 0.15;

    public PremiumMember() {
    }

    public PremiumMember(String name, String ic, int id, String memberHp, String memberType, double discountRate) {
        super(name, ic, id, memberHp, memberType, discountRate);
    }

    public static void setPremiumRate(double premiumRate) {
        PremiumMember.premiumRate = premiumRate;
    }

    public static double getGoldRate() {
        return premiumRate;
    }
    
    @Override
    public double calDiscount(){
        return super.calculateDiscountRate() + premiumRate;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nPREMIUM DISCOUNT RATE: " + premiumRate;
    }

}
