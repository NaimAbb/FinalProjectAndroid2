package android.first.app.finalprojectandroid2;


import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Set;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class TestMocke {


    @Mock
    SharedPreferences sharedPreferences;

    @Before
    public  void before(){
    }

    @Test(expected = NullPointerException.class)
    public  void  testCase(){
        Set<String> set  =  sharedPreferences.getStringSet("Favorite"  ,null);
     //   set = null;
        assertTrue(set.isEmpty());

    }
    @After
    public  void after(){
    }


}
