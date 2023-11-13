package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FiveLogic { 
    
public boolean win(int stoneColor, Map<String, Integer> stoneLocation, List<ArrayList<HashMap<String, Integer>>> stoneArea){//승리 로직에 대한 변수명 같이 통일되도록 정함
    
    
        int color; //돌 색상
        
        //stone location = {h: 0, v: 0} 오목판의 좌표
        int x = stoneLocation.get("h"); 
        int y = stoneLocation.get("v");
        

        //연속된 5개의 바둑돌 방향 배열
        int[][] dir1 = {{-1,-1},{-1,1},{1,0},{0,1}};
        int[][] dir2 = {{1,1},{1,-1},{-1,0},{0,-1}};
        
        
        // i = 방향 ( 위의 배열의 순서대로 : / -> \  ->  -  ->  | 순서로
        for(int i=0; i<dir1.length; i++){
            int cnt=1; //내가 돌을 두었으니까 count 1 먼저하고 시작 
            
            for(int j=1; j<5; j++){ // 방향별로 한칸씩 바둑돌의 위치 확인
                
                //x2 , y2는 내가 입력한 좌표에서 이동한 방향으로의 좌표를 저장한 좌표...
                int x2 = x + dir1[i][0]*j;
                int y2 = y + dir1[i][1]*j;
                
                //원래는 이렇게 따로 작성했다가 색 판별도 동시에 진행해서 조건 맞추고
//              if(!isInRange(x2,y2,map.length, map[0].length))
//                  break;
                
                //isInRange라는 함수를 밖에 따로 선언해서 로직마다 바로바로 가져다가 사용이 가능하도록함
                if(isInRange(x2,y2,stoneArea.size(), stoneArea.get(0).size()) && stoneArea.get(y2).get(x2).get("c") == stoneColor){
                    cnt += 1; //바둑이 바둑판 범위 안에 &&(있고) 색상이 동일한 경우 count +1 
                } else {
                    break;  //아니면 break
                }
            } //여기까지가 dir1에 대한 방향 for문 이었고 
            
            for(int j=1; j<5; j++){ //여기부터 반대방향 dir2에 대한 방향별 한칸씩 위치 확인
                
                int x2 = x + dir2[i][0]*j;
                int y2 = y + dir2[i][1]*j;
                
                if(isInRange(x2,y2,stoneArea.size(), stoneArea.get(0).size()) && stoneArea.get(y2).get(x2).get("c") == stoneColor){
                    cnt += 1;
                } else {
                    break;
                }
                
            }//dir2 for문 닫힘
            
            //동일한 색상을 가진 돌이 연속된게 5인경우 true값이 return 되어서 판별 완료 :)
            if(cnt==5){
                return true;
            }
        }
        return false;
    }
    
    //바둑판 범위 벗어나는지 확인하는 함수
    static boolean isInRange(int x, int y, int xSize, int ySize) { 
        return (x >= 0 && x < xSize && y >= 0 && y < ySize);
    }
    

}