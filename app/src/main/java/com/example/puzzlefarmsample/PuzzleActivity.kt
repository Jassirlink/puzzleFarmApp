package com.example.puzzlefarmsample

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_puzzle.*
import java.lang.reflect.Field
import java.util.*
import android.content.Intent
import android.os.Handler


class PuzzleActivity : AppCompatActivity() {
//
//    lateinit var db: AppDatabase

    companion object {
        private var TOTAL_COLUMNS: Int? = null
        private var DIMENSIONS: Int? = null
        private var PUZZLE: Int? = null

        private var boardColumnWidth = 0
        private var boardColumnHeight = 0
    }

    private val tileListIndexes = mutableListOf<Int>()

    private val isSolved: Boolean
        get() {
            var solved = false
            for (i in tileListIndexes.indices) {
                Log.d(TAG, "solving value: $i")
                Log.d(TAG, "solving value1: " +tileListIndexes[i])
                if (tileListIndexes[i] == i) {
                    solved = true
                } else {
                    solved = false
                    break
                }
            }

            return solved
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puzzle)

//        db = AppDatabase.getAppDatabase(this)!!

        val puzzleName = "3*3"
        try {
            PUZZLE = (1..4).random()
            TOTAL_COLUMNS = intent.extras?.getInt("Columns")
            DIMENSIONS = TOTAL_COLUMNS!! * TOTAL_COLUMNS!!
        }catch (e:Exception)
        {
            println(e)
        }

        init()
        scrambleTileBoard()
        setTileBoardDimensions()
    }

    private fun init() {
        puzzle_grid_view.apply {
            numColumns = TOTAL_COLUMNS!!
            setOnSwipeListener(object : PuzzleGridView.OnSwipeListener {
                override fun onSwipe(direction: SwipeDirections, position: Int) {
                    moveTiles(direction, position)
                }
            })
        }

        tileListIndexes += 0 until DIMENSIONS!!
    }

    private fun scrambleTileBoard() {
        var index: Int
        var tempIndex: Int
        val random = Random()

        for (i in tileListIndexes.size - 1 downTo 1) {
            index = random.nextInt(i + 1)
            tempIndex = tileListIndexes[index]
            tileListIndexes[index] = tileListIndexes[i]
            tileListIndexes[i] = tempIndex
        }
    }

    private fun  setTileBoardDimensions() {
        val observer = puzzle_grid_view.viewTreeObserver
        observer.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                puzzle_grid_view.viewTreeObserver.removeOnGlobalLayoutListener(this)

                val displayWidth = puzzle_grid_view.measuredWidth

                boardColumnWidth = displayWidth / TOTAL_COLUMNS!!
                boardColumnHeight = boardColumnWidth

                displayTileBoard()
            }
        })
    }

    private fun displayTileBoard() {
        val tileImages = mutableListOf<ImageView>()
        var tileImage: ImageView

        tileListIndexes.forEach { i ->
            tileImage = ImageView(this)

            when (i) {
                0 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 1))
                1 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 2))
                2 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 3))
                3 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 4))
                4 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 5))
                5 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 6))
                6 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 7))
                7 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 8))
                8 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 9))
                9 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 10))
                10 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 11))
                11 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 12))
                12 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 13))
                13 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 14))
                14 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 15))
                15 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 16))
                16 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 17))
                17 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 18))
                18 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 19))
                19 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 20))
                20 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 21))
                21 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 22))
                22 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 23))
                23 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 24))
                24 -> tileImage.setBackgroundResource(getImage(PUZZLE!!, TOTAL_COLUMNS!!, 25))
            }

            tileImages.add(tileImage)
        }

        puzzle_grid_view.adapter = TileImageAdapter(tileImages, boardColumnWidth, boardColumnHeight)
    }

    private fun displayToast(@StringRes textResId: Int) {
        Toast.makeText(this, getString(textResId), Toast.LENGTH_LONG).show()
    }

    private fun moveTiles(direction: SwipeDirections, position: Int) {
        // Upper-left-corner tile
        if (position == 0) {
            when (direction) {
                SwipeDirections.RIGHT -> swapTile(position, 1)
                SwipeDirections.DOWN -> swapTile(position, TOTAL_COLUMNS!!)
                else -> displayToast(R.string.puzzle_invalid_move)
            }
            // Upper-center tiles
        } else if (position > 0 && position < TOTAL_COLUMNS!! - 1) {
            when (direction) {
                SwipeDirections.LEFT -> swapTile(position, -1)
                SwipeDirections.DOWN -> swapTile(position, TOTAL_COLUMNS!!)
                SwipeDirections.RIGHT -> swapTile(position, 1)
                else -> displayToast(R.string.puzzle_invalid_move)
            }
            // Upper-right-corner tile
        } else if (position == TOTAL_COLUMNS!! - 1) {
            when (direction) {
                SwipeDirections.LEFT -> swapTile(position, -1)
                SwipeDirections.DOWN -> swapTile(position, TOTAL_COLUMNS!!)
                else -> displayToast(R.string.puzzle_invalid_move)
            }
            // Left-side tiles
        } else if (position > TOTAL_COLUMNS!! - 1 && position < DIMENSIONS!! - TOTAL_COLUMNS!! && position % TOTAL_COLUMNS!! == 0) {
            when (direction) {
                SwipeDirections.UP -> swapTile(position, -TOTAL_COLUMNS!!)
                SwipeDirections.RIGHT -> swapTile(position, 1)
                SwipeDirections.DOWN -> swapTile(position, TOTAL_COLUMNS!!)
                else -> displayToast(R.string.puzzle_invalid_move)
            }
            // Right-side AND bottom-right-corner tiles
        } else if (position == TOTAL_COLUMNS!! * 2 - 1 || position == TOTAL_COLUMNS!! * 3 - 1) {
            when (direction) {
                SwipeDirections.UP -> swapTile(position, -TOTAL_COLUMNS!!)
                SwipeDirections.LEFT -> swapTile(position, -1)
                SwipeDirections.DOWN -> {
                    // Tolerates only the right-side tiles to swap downwards as opposed to the bottom-
                    // right-corner tile.
                    if (position <= DIMENSIONS!! - TOTAL_COLUMNS!! - 1) {
                        swapTile(position, TOTAL_COLUMNS!!)
                    } else {
                        displayToast(R.string.puzzle_invalid_move)
                    }
                }
                else -> displayToast(R.string.puzzle_invalid_move)
            }
            // Bottom-left corner tile
        } else if (position == DIMENSIONS!! - TOTAL_COLUMNS!!) {
            when (direction) {
                SwipeDirections.UP -> swapTile(position, -TOTAL_COLUMNS!!)
                SwipeDirections.RIGHT -> swapTile(position, 1)
                else -> displayToast(R.string.puzzle_invalid_move)
            }
            // Bottom-center tiles
        } else if (position < DIMENSIONS!! - 1 && position > DIMENSIONS!! - TOTAL_COLUMNS!!) {
            when (direction) {
                SwipeDirections.UP -> swapTile(position, -TOTAL_COLUMNS!!)
                SwipeDirections.LEFT -> swapTile(position, -1)
                SwipeDirections.RIGHT -> swapTile(position, 1)
                else -> displayToast(R.string.puzzle_invalid_move)
            }
            // Center tiles
        } else {
            when (direction) {
                SwipeDirections.UP -> swapTile(position, -TOTAL_COLUMNS!!)
                SwipeDirections.LEFT -> swapTile(position, -1)
                SwipeDirections.RIGHT -> swapTile(position, 1)
                else -> swapTile(position, TOTAL_COLUMNS!!)
            }
        }
    }

    private fun swapTile(currentPosition: Int, swap: Int) {
        val newPosition = tileListIndexes[currentPosition + swap]
        tileListIndexes[currentPosition + swap] = tileListIndexes[currentPosition]
        tileListIndexes[currentPosition] = newPosition
        displayTileBoard()
        Log.d(TAG, "swapTile: $isSolved")
        if (isSolved) {
            print("this is solved")
         /*   var disposable = db.getSettingDao().getByName("username")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it != null && it.name.equals("username")) {
                        db.getUserDao().addScore(it.value!!, DIMENSIONS!!)
                            ?.subscribeOn(Schedulers.io())
                            ?.observeOn(AndroidSchedulers.mainThread())
                            ?.subscribe({
                                finish()
                            }, {})
                    }
                }, {})*/

            displayToast(R.string.puzzle_solved)
            Handler().postDelayed({
                //doSomethingHere()
                val i = Intent(this, PuzzleActivity::class.java)
                finish()
                startActivity(i)
            }, 2000)

        }
    }

    fun getImage(puzzle: Int, columns: Int, id: Int): Int {
        val name = "_${puzzle}_${columns}x${columns}_${id}"
        return getResId(name, R.drawable::class.java)
    }

    fun getResId(resName: String?, c: Class<*>): Int {
        return try {
            val idField: Field = c.getDeclaredField(resName!!)
            Log.d(TAG, "getResId: $idField")
            print("this is idfield $idField");
            idField.getInt(idField)
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }
}

enum class SwipeDirections {
    UP, DOWN, LEFT, RIGHT
}