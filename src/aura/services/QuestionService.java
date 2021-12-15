/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import aura.entities.Question;
import aura.entities.Reponse;
import aura.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nour Dekhil
 */
public class QuestionService {

    public static QuestionService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private QuestionService() {
        req = new ConnectionRequest();
    }

    public static QuestionService getInstance() {
        if (instance == null) {
            instance = new QuestionService();
        }
        return instance;
    }

    public ArrayList<Question> showQuestion() {
        ArrayList<Question> result = new ArrayList<>();
        String url = Statics.BASE_URL + "/AllQuestions";
        System.out.println("ddd" + url);

        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser json;
                json = new JSONParser();

                try {
                    Map<String, Object> mapQuestions = json.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    System.out.println(json);
                    List<Map<String, Object>> lists = (List<Map<String, Object>>) mapQuestions.get("root");
                    System.out.println(req.getResponseData());
                    for (Map<String, Object> obj : lists) {
                        Question question = new Question();

                        float id = Float.parseFloat(obj.get("id").toString());
                        String ques = obj.get("question").toString();
                        String categorie = obj.get("categorie").toString();

                        question.setId((int) id);
                        question.setQuestion(ques);
                        question.setCategorie(categorie);
                        result.add(question);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }

    public boolean SupprimerQuestion(int id) {
        String url = Statics.BASE_URL + "/deleteQuestionJSON/" + id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                resultOK = req.getResponseCode() == 200;

                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean UpdateQuestion(int id, String newq, String newc) {
        String url = Statics.BASE_URL + "/updateQuestionJSON/" + id + "?question=" + newq + "&categorie=" + newc;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean addQuestion(String question, String categorie, String username) {
        String url = Statics.BASE_URL + "/addQuestionJson/" + username + "?question=" + question + "&categorie=" + categorie;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

}
