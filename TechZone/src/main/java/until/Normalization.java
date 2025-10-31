/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package until;

/**
 *
 * @author NgKaitou
 */
public class Normalization {
    
    
    public static String normalizeName(String name) {
        if(name == null || name.isBlank()) return "";
        
        StringBuilder normalizedName = new StringBuilder();
        
        String parts[] = name.trim().split("\\s+");
        for (String part : parts) {
            char firstLetter = Character.toUpperCase(part.trim().charAt(0));
            String tail = part.trim().substring(1).toLowerCase();
            normalizedName.append(firstLetter).append(tail).append(" ");
        }
        return normalizedName.toString().trim();
    }

}
