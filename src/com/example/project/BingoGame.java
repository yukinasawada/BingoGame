package com.example.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class BingoGame {

	final int row = 7;
	final int center = row / 2;

	void start() {
		//ビンゴカードとビンゴカードチェックを初期化
		int[][] bingoCard = new int[row][row];
		boolean[][] checkOpened = new boolean[row][row];
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
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < row; j++) {
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

		int[][] bingoCard = new int[row][row];
		ArrayList<ArrayList<Integer>> lists = new ArrayList<ArrayList<Integer>>();

		for (int j = 0; j < row; j++) {

			ArrayList<Integer> list = new ArrayList<>();

			//listに順番に格納
			for (int num = j * 15 + 1; num <= (j + 1) * 15; num++) {
				list.add(num);
			}

			//listをシャッフル
			Collections.shuffle(list);
			lists.add(list);

			//シャッフルした数字をビンゴカードに格納
			for (int i = 0; i < row; i++) {
				bingoCard[i][j] = lists.get(j).get(i);
			}
		}
		return bingoCard;
	}

	int[] checkLines(boolean[][] checkOpened) {
		int reach = 0;
		int bingo = 0;
		/* 上手く動かないのでコメントアウト。
		int columnCount = 0;
		
		//横チェック・縦チェック
		for (int i = 0; i < row; i++) {
			//int count = 0;
			int rowCount = 0;
		
			for (int j = 0; j < row; j++) {
				if (checkOpened[i][j]) {
					rowCount++;
				} else if (checkOpened[j][i]) {
					columnCount++;
				}
			}
			if (rowCount == row && columnCount == row) {
				bingo = bingo + 2;
			} else if (rowCount == row || columnCount == row) {
				bingo++;
			} else if (rowCount == row - 1 && columnCount == row - 1) {
				reach = reach + 2;
			} else if (rowCount == row - 1 || columnCount == row - 1) {
				reach++;
			}
		}
		*/

		//横チェック
		for (int i = 0; i < row; i++) {
			int count = 0;

			for (int j = 0; j < row; j++) {
				if (checkOpened[i][j]) {
					count++;
				}
			}
			if (count == row) {
				bingo++;
			} else if (count == row - 1) {
				reach++;
			}
		}

		//縦チェック
		for (int j = 0; j < row; j++) {
			int count = 0;

			for (int i = 0; i < row; i++) {
				if (checkOpened[i][j]) {
					count++;
				}
			}
			if (count == row) {
				bingo++;
			} else if (count == row - 1) {
				reach++;
			}
		}

		//斜めチェック(左上から右下)
		int count = 0;
		for (int i = 0; i < row; i++) {
			if (checkOpened[i][i]) {
				count++;
			}
		}
		if (count == row) {
			bingo++;
		} else if (count == row - 1) {
			reach++;
		}

		//斜めチェック(右上から左下)
		count = 0;
		for (int i = 0; i < row; i++) {
			if (checkOpened[i][row - 1 - i]) {
				count++;
			}
		}
		if (count == row) {
			bingo++;
		} else if (count == row - 1) {
			reach++;
		}

		int[] result = new int[] { reach, bingo };

		return result;
	}

	void showBingoCard(int[][] bingoCard, boolean[][] checkOpened) {

		for (int i = 0; i < row; i++) {

			for (int j = 0; j < row; j++) {

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
