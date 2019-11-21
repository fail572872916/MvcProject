package com.example.jetpacktest.testScore;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScoreViewModel  extends ViewModel {

    public MutableLiveData<Integer> screA;
    public MutableLiveData<Integer> screB;
    public  int  tempScoreA,tempScoreB;

    public MutableLiveData<Integer> getScreA() {
        if(screA==null){
            screA=new MutableLiveData<>();
            screA.setValue(0);
        }
        return screA;
    }

    public MutableLiveData<Integer> getScreB() {
    if(screB==null){
        screB=new MutableLiveData<>();
        screB.setValue(0);
    }
        return screB;
    }

    public  void AddTeamA(int p){
        tempScoreA=screA.getValue();
        tempScoreB=screB.getValue();
        screA.setValue(screA.getValue()+p);
    }

    public  void AddTeamB(int p){
        tempScoreA=screA.getValue();
        tempScoreB=screB.getValue();

        screB.setValue(screB.getValue()+p);
    }

    public  void  ResetScore(){
        tempScoreA=screA.getValue();
        tempScoreB=screB.getValue();
        screB.setValue(0);
        screA.setValue(0);
    }
    public  void ReBack(){

      screB.setValue(tempScoreB);
      screA.setValue(tempScoreA);
    }
}
