/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

/**
 *
 * @author evansleong
 */
public class GoldMember extends Membership {

    private static double goldRate = 0.10;

    public GoldMember() {
    }

    public GoldMember(String name, String ic, int id, String memberHp, String memberType, double discountRate) {
        super(name, ic, id, memberHp, memberType, discountRate);
    }

    public static void setGoldRate(double goldRate) {
        GoldMember.goldRate = goldRate;
    }

    public static double getGoldRate() {
        return goldRate;
    }

    @Override
    public double calDiscount() {
        return super.calculateDiscountRate() + goldRate;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nGOLD DISCOUNT RATE: " + goldRate;
    }

}
