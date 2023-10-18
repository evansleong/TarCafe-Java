/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

/**
 *
 * @author evansleong
 */
public class NormalMember extends Membership {

    private static double normalRate = 0.05;

    public NormalMember() {
    }

    public NormalMember(String name, String ic, int id, String memberHp, String memberType, double discountRate) {
        super(name, ic, id, memberHp, memberType, discountRate);
    }

    public static void setNormalRate(double normalRate) {
        NormalMember.normalRate = normalRate;
    }

    public static double getNormalRate() {
        return normalRate;
    }

    @Override
    public double calDiscount() {
        return super.calculateDiscountRate() + normalRate;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nNORMAL DISCOUNT RATE: " + normalRate;
    }

}
