package MainRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Rank {
        public static int cardRank(LinkedList<Card> cards) {
        /*
         * 다섯 장을 판별하는 방법:
         * 1번째 카드: 2~5번째 카드와 비교
         * 2번째 카드: 3~5번째 카드와 비교
         * 3번째 카드: 4~5번째 카드와 비교
         * 4번째 카드: 5번째 카드와 비교
         * 
         * i == 0, j == 1~4
         * i == 1, j == 2~4
         * i == 2, j == 3~4
         * i == 3, j == 4
         */
        int pairCount = 0;
        for (int i = 0; i < 4; ++i) {
            for (int j = i + 1; j < 5; ++j) {
                if (cards.get(i).number == cards.get(j).number) {
                    pairCount++;
                    /*
                     * 원페어: 1
                     * 투페어: 2
                     * 트리플: 3
                     * 풀하우스: 4 ∵ 3 + 1
                     * 포카드: 6 ∵ 2s, 2h, 2d, 2c, 3c일 때 똑같은 숫자의 쌍: 2s-2h, 2s-2d, 2s-2c, 2h-2d, 2h-2c, 2d-2c
                     */
                }
            }
        }

        // 숫자별 정렬
        LinkedList<Integer> sortedNum = new LinkedList<>();
        for (int i = 0; i < 5; ++i) {
            sortedNum.add(cards.get(i).number); // sortedNum에 값 복사: cards의 number값
        }
        Collections.sort(sortedNum);        

        // straight 여부 판정
        boolean straight = false;
        if (pairCount == 0) {
            // [1, 2, 3, 4, 5], [2, 3, 4, 5, 6], …, [9, 10, 11, 12, 13]: 가장 큰 수와 가장 작은 수의 차가 4
            if (sortedNum.get(4) - sortedNum.get(0) == 4) {
                straight = true;
            }
            if (sortedNum.get(0) == 1 && sortedNum.get(1) == 10) { // [A, 10, J, Q, K]
                straight = true;
            }
        }

        // flush 여부 판정
        LinkedList<String> sortedSuit = new LinkedList<>(); // suit: 문양별 정렬
        for (int i = 0; i < 5; ++i) {
            sortedSuit.add(cards.get(i).suit); // sortedSuit에 값 복사: cards의 suit값
        }
        Collections.sort(sortedSuit);

        // 문양별로 정렬한 후 suit[0]과 suit[4]가 같을 경우, index 0~4까지 같은 문양
        boolean flush = false;
        if (sortedSuit.get(0).equals(sortedSuit.get(4))) {
            flush = true;
        }

        /* 
         * rank: 숫자가 클수록 높은 순위
         * 총 8자리:
         * 1~2: 족보
         * 3~4: 페어(1)
         * 5~6: 페어(2)
         * 7~8: 하이 카드
         */ 
        
        int rank = 2_00_00_00;
        if (straight && flush) {
            rank = 10_00_00_00;
        } else if (pairCount == 6) { // 포카드
            rank = 9_00_00_00;
        } else if (pairCount == 4) { // 풀하우스
            rank = 8_00_00_00;
        } else if (flush) {
            rank = 7_00_00_00;
        } else if (straight) {
            rank = 6_00_00_00;
        } else if (pairCount == 3) {
            rank = 5_00_00_00;
        } else if (pairCount == 2) {
            rank = 4_00_00_00;
        } else if (pairCount == 1) {
            rank = 3_00_00_00;
        } else {                     // 하이 카드(족보)
            rank = 2_00_00_00;
        }

        // highCard 정보를 덧셈 형식으로 추가, 형식 예시: rank = 700 + highCard;
        rank += highCard(sortedNum, rank);

        return rank;
    }

    // 각 족보의 하이 카드 return: 같은 rank일 때 족보를 따져보는 용도
    public static int highCard(LinkedList<Integer> sortedNum, int rank) {
        // 계산의 편리를 위해 sortedNum의 1(A) -> 14로 변환
        for (int i = 0; i < sortedNum.size(); ++i) {
            if (sortedNum.get(i) == 1) {
                sortedNum.set(i, 14);
            }
        }
        Collections.sort(sortedNum);

        switch (rank) {
            // 5장 내에서 판단해야 하는 경우 중 스트레이트 플러시, 플러시, 스트레이트, 하이 카드
            case 10_00_00_00:
            case 7_00_00_00:
            case 6_00_00_00:
            case 2_00_00_00:
                return sortedNum.getLast();
            // 풀하우스
            case 8_00_00_00:
                /* 
                * 풀하우스: 트리플 + 투페어, 트리플의 족보 > 투페어의 족보 > 키커의 족보
                * 2 2 2 9 9 < 3 3 4 4 4
                * -> 3~4: 트리플, 5~6: 페어, 키커 별도 확인: 5장씩 비교해서 비교 불가
                * 
                * 어떤 수가 트리플을 이루는지 확인
                * arr1: 2 2 2 9 9 < arr2: 3 3 4 4 4,
                * arr1[0] == arr1[2], arr2[0] != arr2[2]
                */
                if (sortedNum.get(0) == sortedNum.get(2)) {
                    return sortedNum.get(2) * 10000 + sortedNum.get(4) * 100;
                } else {
                    return sortedNum.get(2) * 10000 + sortedNum.get(0) * 100;
                }
            // 포카드, 트리플, 페어 등 5장 내에서 판단하지 않을 경우
            // 포카드: 5 5 5 5 6 / 5 6 6 6 6
            case 9_00_00_00:
                // 5 5 5 5 6일 경우
                if (sortedNum.get(0) == sortedNum.get(3)) {
                    return (sortedNum.get(0) * 10000 + sortedNum.get(4)); // 포카드 + 키커
                // 5 6 6 6 6일 경우
                } else { 
                    return sortedNum.get(4) * 10000 + sortedNum.get(0); // 포카드 + 키커
                }
            // 트리플: 3 3 3 4 5 / 3 4 4 4 5 / 3 4 5 5 5
            case 5_00_00_00:
                // 3 3 3 4 5일 경우
                if (sortedNum.get(0) == sortedNum.get(2)) {
                    // 트리플 값 + 키커1 + 키커2
                    return (sortedNum.get(0) * 10000 + sortedNum.get(4) * 100 + sortedNum.get(3));
                } else if (sortedNum.get(1) == sortedNum.get(3)) {
                // 3 4 4 4 5일 경우 
                    return (sortedNum.get(1) * 10000 + sortedNum.get(4) * 100 + sortedNum.get(0));
                } else { // 3 4 5 5 5일 경우
                    return (sortedNum.get(2) * 10000 + sortedNum.get(1) * 100 + sortedNum.get(0));
                }
            // 원 페어: 2 2 3 4 5 / 2 3 3 4 5 / 2 3 4 4 5 / 2 3 4 5 5
            case 3_00_00_00:
                List<Integer> kickers = new ArrayList<>();
                int pairIndex = -1;
                // 페어 찾기
                for (int i = 0; i < 4; i++) {
                    if (sortedNum.get(i) == sortedNum.get(i + 1)) {
                        pairIndex = sortedNum.get(i);
                        break;
                    }
                }
                // 킥커 수집
                for (int i = 0; i < 5; i++) {
                    if (sortedNum.get(i) != pairIndex) {
                        kickers.add(sortedNum.get(i));
                    }
                }
                Collections.sort(kickers);
                // 페어 + 3개 킥커        
                return (pairIndex * 10000 + kickers.get(2) * 100 + kickers.get(1) + kickers.get(0)); 
            // 투 페어: 2 2 3 3 4 / 2 2 3 4 4 / 2 3 3 4 4
            case 4_00_00_00:              
                int firstPair, secondPair, kicker;
                // 2 2 3 3 4일 경우
                if (sortedNum.get(0) == sortedNum.get(1) && sortedNum.get(2) == sortedNum.get(3)) {
                    firstPair = sortedNum.get(2);
                    secondPair = sortedNum.get(0);
                    kicker = sortedNum.get(4);
                // 2 2 3 4 4일 경우
                } else if (sortedNum.get(0) == sortedNum.get(1) && 
                            sortedNum.get(3) == sortedNum.get(4)) { 
                    firstPair = sortedNum.get(3);
                    secondPair = sortedNum.get(0);
                    kicker = sortedNum.get(2);
                } else { // 2 3 3 4 4일 경우
                    firstPair = sortedNum.get(4);
                    secondPair = sortedNum.get(2);
                    kicker = sortedNum.get(0);
                }
                return (firstPair * 10000 + secondPair * 100 + kicker); // 페어1 > 페어2 > 키커
            }
            return rank;
        }
    
    public static int bestRank(LinkedList<Card> cards) {
        int bestRank = 2_00_00_00; // 가장 낮은 랭크로 초기화
        /*
         * 7장의 카드 중 5장을 선택하는 모든 조합을 확인
         * i = 0, j = 1 ~ 6까지 검사
         * i = 1, j = 2 ~ 6까지 검사
         * ... 
         * i = 5, j = 6까지 검사
         */ 
    
        for (int i = 0; i < cards.size(); ++i) {
            for (int j = i + 1; j < cards.size(); ++j) {
                LinkedList<Card> temp = new LinkedList<>(cards); // temp에 cards의 값 복사
                
                // i, j의 카드 제외: 뒤의 index부터 제거해야 오류 X
                temp.remove(j);
                temp.remove(i);

                int rank = cardRank(temp); // 선별된 5장의 카드 랭크 계산
                if (rank > bestRank) {
                    bestRank = rank; // 더 좋은 랭크가 나오면 bestRank에 업데이트
                }
            }
        }
        return bestRank;
    }
    
    public static void determineWinner(Player[] players) {
        int maxRank = 0; // 최대 랭크값 초기화
        List<Integer> winnersIndex = new ArrayList<>(); // 우승자의 인덱스를 저장할 리스트
    
        // 각 플레이어의 최종 순위를 계산하여 최대 랭크를 찾음
        for (int i = 0; i < players.length; i++) {
            int rank = bestRank(players[i].hands); // 최종 랭크 계산
            if (rank > maxRank) {
                maxRank = rank; // 더 높은 순위 발견 시 최대 랭크 업데이트
                winnersIndex.clear(); // 이전 우승자 인덱스 초기화
                winnersIndex.add(i); // 현재 플레이어를 우승자로 설정
            } else if (rank == maxRank) {
                winnersIndex.add(i); // 최대 랭크를 가진 플레이어를 우승자로 추가
            }
        }
    
        // 우승자 출력
        if (winnersIndex.size() == 1) {
            System.out.println("우승자는 플레이어 " + (winnersIndex.get(0) + 1) + "입니다!");
        } else {
            System.out.print("동점자(Split): ");
                for (int i = 0; i < winnersIndex.size(); i++) {
                    System.out.print("플레이어 " + (winnersIndex.get(i) + 1) + ", ");
                }
                System.out.println("입니다!");
            
        }
    }
}
