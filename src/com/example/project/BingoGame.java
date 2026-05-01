package com.example.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class BingoGame {

	final int siz = 7;
	final int center = siz / 2;

	void start() {
		//ビンゴカードとビンゴカードチェックを初期化
		int[][] bingoCard = new int[siz][siz];
		boolean[][] checkOpened = new boolean[siz][siz];
		int pickNumber = 0;

		//FREEに予めtureを入れておく
		checkOpened[center][center] = true;

		//ランダムなビンゴカードを作成		  
		bingoCard = creatBingoCard();

		//ランダムな1～105の数字のリストを作成
		List<Integer> pickList = createPickNumber();

		//出たボールとカードの状態を表示する(105回施行)
		for (int pickCount = 1; pickCount <= 105; pickCount++) {

			pickNumber = pickList.get(pickCount - 1);
			System.out.println("ball[" + pickCount + "]:" + pickNumber);

			//出たボールがカード内にあればcheckOpenedをtrueにする
			for (int i = 0; i < siz; i++) {
				for (int j = 0; j < siz; j++) {
					//pickNumberと一致したときTrueにする
					if (bingoCard[i][j] == pickNumber) {
						checkOpened[i][j] = true;
					}
				}
			}

			//ビンゴカードを表示
			showBingoCard(bingoCard, checkOpened);

			//リーチ数、ビンゴ数を確認
			int[] result = checkLines(checkOpened);

			//リーチとビンゴ数を表示
			System.out.println("REACH: " + result[0]);
			System.out.println("BINGO: " + result[1]);

			System.out.println("--------------------");
		}
	}

	List<Integer> createPickNumber() {
		//ランダムで1～105を引く
		List<Integer> pickList = new ArrayList<>();

		//pickListに順番に格納
		for (int i = 1; i <= 105; i++) {
			pickList.add(i);
		}

		//pickListをシャッフル
		Collections.shuffle(pickList);

		return pickList;
	}

	int[][] creatBingoCard() {

		int[][] bingoCard = new int[siz][siz];
		ArrayList<ArrayList<Integer>> lists = new ArrayList<ArrayList<Integer>>();

		for (int j = 0; j < siz; j++) {

			ArrayList<Integer> list = new ArrayList<>();

			//listに順番に格納
			for (int num = j * 15 + 1; num <= (j + 1) * 15; num++) {
				list.add(num);
			}

			//listをシャッフル
			Collections.shuffle(list);
			lists.add(list);

			//シャッフルした数字をビンゴカードに格納
			for (int i = 0; i < siz; i++) {
				bingoCard[i][j] = lists.get(j).get(i);
			}
		}
		return bingoCard;
	}

	int[] checkLines(boolean[][] checkOpened) {
		int reach = 0;
		int bingo = 0;

		int[] rowCountList = new int[siz];
		int[] columnCountList = new int[siz];
		int leftDiagonalCount = 0;
		int rightDiagonalCount = 0;

		//横チェック・縦チェック・斜めチェック
		for (int i = 0; i < siz; i++) {
			for (int j = 0; j < siz; j++) {
				if (checkOpened[i][j]) {
					rowCountList[i] = rowCountList[i] + 1;
					columnCountList[j] = columnCountList[j] + 1;
				}
			}
		}

		for (int i = 0; i < siz; i++) {

			if (rowCountList[i] == siz && columnCountList[i] == siz) {
				bingo = bingo + 2;
			} else if (rowCountList[i] == siz || columnCountList[i] == siz) {
				bingo++;
			}
			if (rowCountList[i] == siz - 1 && columnCountList[i] == siz - 1) {
				reach = reach + 2;
			} else if (rowCountList[i] == siz - 1 || columnCountList[i] == siz - 1) {
				reach++;
			}

			if (checkOpened[i][i]) {
				leftDiagonalCount++;
			}
			if (checkOpened[i][siz - 1 - i]) {
				rightDiagonalCount++;
			}
		}

		if (leftDiagonalCount == siz && rightDiagonalCount == siz) {
			bingo = bingo + 2;
		} else if (leftDiagonalCount == siz || rightDiagonalCount == siz) {
			bingo++;
		}
		if (leftDiagonalCount == siz - 1 && rightDiagonalCount == siz - 1) {
			reach = reach + 2;
		} else if (leftDiagonalCount == siz - 1 || rightDiagonalCount == siz - 1) {
			reach++;
		}

		int[] result = new int[] { reach, bingo };

		return result;
	}

	void showBingoCard(int[][] bingoCard, boolean[][] checkOpened) {

		for (int i = 0; i < siz; i++) {

			for (int j = 0; j < siz; j++) {

				//真ん中はFREEを表示
				if (i == center && j == center) {
					System.out.print(" FREE");

				} else if (checkOpened[i][j]) {
					//10未満の場合は0を付けて表示
					System.out.printf("(%03d)", bingoCard[i][j]);

				} else {
					//10未満の場合は0を付けて表示
					System.out.printf(" %03d ", bingoCard[i][j]);
				}
			}
			//改行
			System.out.println();
		}
		//改行
		System.out.println();
	}
}
