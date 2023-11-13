package logic;
    
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    
    
public class ThreeCheck { //33 판단은 진주언니의 도움이 많았당 :)

        public boolean win(int stoneColor, Map<String, Integer> stoneLocation, List<ArrayList<HashMap<String, Integer>>> stoneArea){
            
            int color;
            
            //stone location = {h: 0, v: 0} 오목판의 좌표
            int x = stoneLocation.get("h"); 
            int y = stoneLocation.get("v");
            
            
            // 8방향 ( 오른쪽, 오른쪽 아래, 아래, 왼쪽 아래, 왼쪽, 왼쪽 위, 위, 오른쪽 위 ) =>  8방향을 이렇게 표현 가능 습득 :)
            int[][] dxy = {{0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}};
            
            
            // 각 방향으로 쭉 가보면서 3개가 되면, 한번 더 가보고 비어있는지 확인 & 원래 좌표에서 반대 방향으로 한칸 갔을 때 비어있는지 확인
            int cnt = 0;
            for (int i=0; i < dxy.length; i++) {
               // 다음 위치
               int nx = x + dxy[i][0];
               int ny = y + dxy[i][1];
               
               // 이 방향으로 3개가 되는 지 확인
               int triple = 1;
               while (isInRange(nx, ny, stoneArea.size(), stoneArea.get(0).size()) && stoneArea.get(nx).get(ny).get("c") == stoneColor) {
                  triple += 1;
                  nx += dxy[i][0];
                  ny += dxy[i][1];
               }
               
               // 다음 위치가 범위를 벗어나거나 색이 다르면 반복 끝
               // 이 때 3개인지 확인
               // 3개라면 한번 더 가봤을 때 비어있는지 & 원래 위치에서 반대로 한칸 갔을 때 비어있는지 확인
               if (triple == 3) {
                  if (isInRange(nx, ny, stoneArea.size(), stoneArea.get(0).size()) && stoneArea.get(nx).get(ny).get("c") == 0) {
                     i = (i + 4) % 8; // 반대방향 확인하는 알고리즘
                     int rev_x = x + dxy[i][0];
                     int rev_y = y + dxy[i][1];
                     if (isInRange(rev_x, rev_y, stoneArea.size(), stoneArea.get(0).size()) && stoneArea.get(nx).get(ny).get("c") == 0) {
                        cnt += 1;
                     }
                  }
               }
            }
            
            // 모든 방향을 다 봤을 때, 조건에 맞는 방향이 2개 이상이라면 33 조건 성립
            if (cnt >= 2) {
               return true;
            }
            return false;
            
            
        }
        
        //바둑판 범위 벗어나는지 확인하기
        static boolean isInRange(int x, int y, int xSize, int ySize) {
            return (x >= 0 && x < xSize && y >= 0 && y < ySize);
        }
    }
