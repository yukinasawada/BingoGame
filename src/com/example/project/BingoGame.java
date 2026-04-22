package com.example.project;

import java.util.ArrayList;
import java.util.Collections;

class BingoGame {

	void start() {
		//ビンゴカードとビンゴカードチェックを初期化
		int[][] bingoCard = new int[5][5];
		boolean[][] checkOpened = new boolean[5][5];
		int pickNumber = 0;

		//FREEに予めtureを入れておく
		checkOpened[2][2] = true;

		//ランダムなビンゴカードを作成		  
		bingoCard = creatBingoCard();

		//ランダムで1～75を引く
		ArrayList<Integer> pickList = new ArrayList<>();

		//pickListに順番に格納
		for (int i = 1; i <= 75; i++) {
			pickList.add(i);
		}

		//pickListをシャッフル
		Collections.shuffle(pickList);

		//出たボールとカードの状態を表示する(75回施行)
		for (int pickCount = 1; pickCount <= 75; pickCount++) {

			pickNumber = pickList.get(pickCount - 1);

			System.out.println("ball[" + pickCount + "]:" + pickNumber);

			//出たボールがカード内にあればcheckOpenedをtrueにする
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
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

	int[][] creatBingoCard() {

		int[][] bingoCard = new int[5][5];
		ArrayList<ArrayList<Integer>> lists = new ArrayList<ArrayList<Integer>>();

		for (int j = 0; j < 5; j++) {

			ArrayList<Integer> list = new ArrayList<>();

			//listに順番に格納
			for (int num = j * 15 + 1; num <= (j + 1) * 15; num++) {
				list.add(num);
			}

			//listをシャッフル
			Collections.shuffle(list);
			lists.add(list);

			//シャッフルした数字をビンゴカードに格納
			for (int i = 0; i < 5; i++) {
				bingoCard[i][j] = lists.get(j).get(i);
			}
		}

		return bingoCard;
	}

	int[] checkLines(boolean[][] checkOpened) {
		int reach = 0;
		int bingo = 0;

		//横チェック
		for (int i = 0; i < 5; i++) {
			int count = 0;

			for (int j = 0; j < 5; j++) {
				if (checkOpened[i][j] == true) {
					count++;
				}
			}
			if (count == 5) {
				bingo++;
			} else if (count == 4) {
				reach++;
			}
		}

		//縦チェック
		for (int j = 0; j < 5; j++) {
			int count = 0;

			for (int i = 0; i < 5; i++) {
				if (checkOpened[i][j] == true) {
					count++;
				}
			}
			if (count == 5) {
				bingo++;
			} else if (count == 4) {
				reach++;
			}
		}

		//斜めチェック(左上から右下)
		int count = 0;
		for (int i = 0; i < 5; i++) {
			if (checkOpened[i][i] == true) {
				count++;
			}
		}
		if (count == 5) {
			bingo++;
		} else if (count == 4) {
			reach++;
		}

		//斜めチェック(右上から左下)
		count = 0;
		for (int i = 0; i < 5; i++) {
			if (checkOpened[i][4 - i] == true) {
				count++;
			}
		}
		if (count == 5) {
			bingo++;
		} else if (count == 4) {
			reach++;
		}

		int[] result = new int[] { reach, bingo };

		return result;
	}

	void showBingoCard(int[][] bingoCard, boolean[][] checkOpened) {

		for (int i = 0; i < 5; i++) {

			for (int j = 0; j < 5; j++) {

				//真ん中はFREEを表示
				if (i == 2 && j == 2) {
					System.out.print("FREE");

				} else if (checkOpened[i][j]) {
					//10未満の場合は0を付けて表示
					if (bingoCard[i][j] < 10) {
						System.out.print("(0" + bingoCard[i][j] + ")");

					} else {
						System.out.print("(" + bingoCard[i][j] + ")");
					}

				} else {
					//10未満の場合は0を付けて表示
					if (bingoCard[i][j] < 10) {
						System.out.print(" 0" + bingoCard[i][j] + " ");

					} else {
						System.out.print(" " + bingoCard[i][j] + " ");

					}
				}
			}
			//改行
			System.out.println();
		}
		//改行
		System.out.println();
	}
}
