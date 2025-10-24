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
        for (int i = 0; i < parts.length; i++) {
            char firstLetter = Character.toUpperCase(parts[i].trim().charAt(0));
            String tail = parts[i].trim().substring(1).toLowerCase();
            normalizedName.append(firstLetter).append(tail).append(" ");
        }
        return normalizedName.toString().trim();
    }

}
