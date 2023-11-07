package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThreeLogic {
    
    private static int[][] dir1 = {{-1,-1},{-1,1},{1,0},{0,1}};
    private static int[][] dir2 = {{1,1},{1,-1},{-1,0},{0,-1}};
    
public boolean win(int stoneColor, Map<String, Integer> stoneLocation, List<ArrayList<HashMap<String, Integer>>> stoneArea){ 
    
        int crossCount = 3;

        int color; 
        //int color = stoneColor.get("c");
        int x = stoneLocation.get("h"); //stone location = {h: 0, v: 0} 오목판의 좌표
        int y = stoneLocation.get("v");

        int[][] oneLine = new int[crossCount][2];
        oneLine[0] = new int[] {x,y}; 
        
        
        for(int i=0; i<dir1.length; i++){
            int cnt=1;
            
            for(int j=1; j<crossCount; j++){
                int x2 = x + dir1[i][0]*j;
                int y2 = y + dir1[i][1]*j;
                
                if(isInRange(x2,y2,stoneArea.size(), stoneArea.get(0).size()) && stoneArea.get(y2).get(x2).get("c") == stoneColor){
                    oneLine[cnt] = new int[] {x2, y2};
                    cnt += 1;
                } else {
                    break;
                }
            }
            
            for(int j=1; j<crossCount; j++){
                int x2 = x + dir2[i][0]*j;
                int y2 = y + dir2[i][1]*j;
                
                if(isInRange(x2,y2,stoneArea.size(), stoneArea.get(0).size()) && stoneArea.get(y2).get(x2).get("c") == stoneColor){
                    if(cnt == crossCount) {
                        break;
                    }
                    oneLine[cnt] = new int[] {x2, y2};
                    cnt += 1;
                } else {
                    break;
                }
                
            }
            
            
            
            if(cnt==crossCount){           
                for(int w=0; w<crossCount; w++) {
                    for(int k=0; k<dir1.length; k++){
                        if(k == i) { 
                            continue; 
                        }
//                        int cnt=1;
                        
                        for(int j=1; j<crossCount; j++){
                            int x2 = oneLine[w][0] + dir1[k][0]*j;
                            int y2 = oneLine[w][1] + dir1[k][1]*j;
                            
                            if(isInRange(x2,y2,stoneArea.size(),stoneArea.get(0).size()) && stoneArea.get(y2).get(x2).get("c") == stoneColor){
                                cnt += 1;
                            } else {
                                break;
                            }
                        }
                        
                        for(int j=1; j<crossCount; j++){
                            int x2 = oneLine[w][0] + dir2[k][0]*j;
                            int y2 = oneLine[w][1] + dir2[k][1]*j;
                            
                            if(isInRange(x2,y2,stoneArea.size(),stoneArea.get(0).size()) && stoneArea.get(y2).get(x2).get("c") == stoneColor){
                                cnt += 1;
                            } else {
                                break;
                            }
                            
                        }
                        
                        if(cnt == crossCount) {
                            return true;
                        }
                    
                    }
                }   
                
          }
        }
        return false;
        
    }

//    boolean checkLine(String color,int[][] oneLine,int i,int[][] map) {
//        
//    }
    

    boolean isInRange(int x, int y, int xSize, int ySize) {
        return (x >= 0 && x < xSize && y >= 0 && y < ySize);
    }

}