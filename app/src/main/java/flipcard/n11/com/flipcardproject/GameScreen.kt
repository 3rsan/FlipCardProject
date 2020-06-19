package flipcard.n11.com.flipcardproject

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_game_screen.*
import kotlinx.android.synthetic.main.grid_item.view.*
import kotlin.collections.ArrayList
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*


class GameScreen : Fragment() {

    private var adapter : CardAdapter?=null
    private var cardList = ArrayList<Card>()
    private var firstCard : Card? =null
    private var firstCardView: View? =null
    private var inProgress: Boolean = false
    private var numberOfVisibleCard: Int =0
    private var currentLevel: Int =1
    private var tvTimer: TextView?=null
    var score: Int=0
    var minutes:Long=0
    var second:Long=0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // load cards

        val v = inflater.inflate(R.layout.fragment_game_screen, container, false)
        val context = activity as AppCompatActivity

         val gvCards: RecyclerView = v.findViewById(R.id.gvCards)

        gvCards.setLayoutManager(GridLayoutManager(context, 4))
        tvTimer=v.findViewById(R.id.tvTimer)

        //load level1
        startLevel()
        timer()

        adapter = CardAdapter(cardList)

        gvCards.adapter = adapter

        // Usage:
        gvCards.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                // value of item that is clicked

                val card = cardList[position]

                var isMatched:Boolean=handleSelection(view,card)
                if(isMatched){
                    numberOfVisibleCard-=2
                }
            }
        })

        return v

    }


    fun handleSelection(cardView : View , card: Card):Boolean
    {
        //adapter?.modelViewHolder?.bindItem(card)
        cardView.imgCard.setImageResource(card.image!!)

        if (!inProgress)
        {

            if (firstCard == null)
            {

                firstCard = card
                firstCardView = cardView
                cardView.imgCard.setImageResource(card.image!!)

            }
            else if (firstCard?.name.equals(card.name) && firstCard?.id != card.id)
            {

                firstCard = null
                inProgress = true
                var handler = Handler()
                var runnable = Runnable {
                    cardView.visibility = View.INVISIBLE
                    firstCardView?.visibility = View.INVISIBLE

                    txtScore.setText(calculateScore().toString())

                    inProgress = false
                    if(numberOfVisibleCard==0){
                        startLevel()

                    }

                }

                handler.postDelayed(runnable, 250)
                return true
            }
            else
            {

                firstCard = null
                inProgress = true
                var handler = Handler()
                var runnable = Runnable{
                    firstCardView?.imgCard?.setImageResource(R.drawable.diomand!!)
                    cardView.imgCard.setImageResource(R.drawable.diomand!!)
                    inProgress = false
                }
                handler.postDelayed(runnable, 500)

            }
        }
        return false
    }
    fun startLevel(){

        if(currentLevel==1) {
            cardList.add(Card("gandalf", R.drawable.gandalf, 1))
            cardList.add(Card("gandalf", R.drawable.gandalf, 2))
            cardList.add(Card("aragorn", R.drawable.aragorn, 3))
            cardList.add(Card("aragorn", R.drawable.aragorn, 4))
            cardList.add(Card("sam", R.drawable.sam, 5))
            cardList.add(Card("sam", R.drawable.sam, 6))

        }
        else if(currentLevel==2) {
            cardList.add(Card("legolas", R.drawable.legolas, 7))
            cardList.add(Card("legolas", R.drawable.legolas, 8))
        }
        else if(currentLevel==3) {
            cardList.add(Card("gimli", R.drawable.gimli, 9))
            cardList.add(Card("gimli", R.drawable.gimli, 10))
            cardList.add(Card("eomer", R.drawable.eomer, 11))
            cardList.add(Card("eomer", R.drawable.eomer, 12))
        }
        else if(currentLevel==4){
            cardList.add(Card("arwen", R.drawable.arwen, 13))
            cardList.add(Card("arwen", R.drawable.arwen, 14))
            cardList.add(Card("eowyn", R.drawable.eowyn, 15))
            cardList.add(Card("eowyn", R.drawable.eowyn, 16))

        }else {
            val context = activity as AppCompatActivity
            context.replaceFragment(Home())
            //txtFinalScore.setText(finalCalculateScore().toString())

        }
        cardList.shuffle()

        currentLevel++

        numberOfVisibleCard=cardList.size

        if(currentLevel!=1){

            adapter = CardAdapter(cardList)

            gvCards?.adapter = adapter
            //adapter?.notifyDataSetChanged()
        }

    }
    fun timer (){

        var countDownTimer = object : CountDownTimer(180000, 1000){
            override fun onTick(millisUntilFinished: Long){
                var remainingSeconds:Long = millisUntilFinished/1000
                minutes = (remainingSeconds/60)
                second =(remainingSeconds%60)
                var strTime=String.format("%02d:%02d", minutes, second)
                tvTimer?.setText(strTime)

            }

            override fun onFinish() {
                val context = activity as AppCompatActivity
                context.replaceFragment(Home())
            }

        }
        countDownTimer.start()



    }
    fun AppCompatActivity.replaceFragment(fragment:Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun levelStartPoint () : Int{

       // score=score+currentLevel

        return 1
    }

    fun remainingTime () : Long{

        return minutes*60 +second
    }

    fun calculateScore () : Int{


        score=(score+levelStartPoint()) * (currentLevel-1)

        return score
    }
    fun finalCalculateScore (): Int{

        score=score+(remainingTime().toInt())
        return score

    }

}