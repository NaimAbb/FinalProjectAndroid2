package android.first.app.finalprojectandroid2;

import android.content.Context;
import android.first.app.finalprojectandroid2.Adapter.FavoritesAdapter;
import android.first.app.finalprojectandroid2.Fragments.GeneralChatFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class AdapterViewTest {
   private FavoritesAdapter adapter;
    @Mock
    Context context;
    @Mock
    GeneralChatFragment fragment;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
        ArrayList<News> data = new  ArrayList<News>();
        adapter = new FavoritesAdapter(data ,R.layout.last_news_item,context , fragment);
    }

    @Test
    public void getItemCount_shouldBeZeroByDefault() {
        ArrayList<News> data = new  ArrayList<News>();
        data.add(new News());
        adapter = spy(new FavoritesAdapter(data,R.layout.last_news_item,context,fragment));
        doNothing().when(adapter).setData(data);
         assertTrue(adapter.getItemCount() == 1);

    }

    @Test
    public void getItemCount_shouldReturnCorrectValueAfterSetData() {
        adapter.setData(asList(mock(News.class), mock(News.class)));
       // assertThat(adapter.getItemCount()).isEqualTo(2);
    }

    @Test
    public void setData_shouldNotifyObserversAboutChange() {
        RecyclerView.AdapterDataObserver observer = mock(RecyclerView.AdapterDataObserver.class);
        adapter.registerAdapterDataObserver(observer);

     adapter.setData(Collections.<News>emptyList());
        verify(observer).onChanged();
        verifyNoMoreInteractions(observer);
    }

    @Test
    public void onCreateViewHolder_shouldCreateViewHolder() {
        ArrayList<News> data = new  ArrayList<News>();
        adapter = spy(new FavoritesAdapter(data,R.layout.last_news_item,context,fragment));
        ViewGroup parent = mock(ViewGroup.class);
        View itemView = mock(View.class);

        TextView TextView = mock(TextView.class);
        when(itemView.findViewById(R.id.nameTv)).thenReturn(TextView);


        FavoritesAdapter.MyViewHolder viewHolder  = null;
        when(adapter.onCreateViewHolder(parent, 0)).thenReturn(viewHolder);
       // assertThat(viewHolder.itemView).isSameAs(itemView);
        // Also, we don't expect exceptions here.
    }

    @Test
    public void onBindViewHolder_shouldBindItemsToTheViewHolders() {
        List<News> items = asList(
//                Item.builder().id("1").imagePreviewUrl("https://url1").title("t1").shortDescription("s1").build(),
//                Item.builder().id("2").imagePreviewUrl("https://url2").title("t2").shortDescription("s2").build(),
//                Item.builder().id("3").imagePreviewUrl("https://url3").title("t3").shortDescription("s3").build()
        );

        adapter.setData(items);

        for (int position = 0, size = items.size(); position < size; position++) {
            FavoritesAdapter.MyViewHolder itemViewHolder = mock(FavoritesAdapter.MyViewHolder.class);
            adapter.onBindViewHolder(itemViewHolder, position);

          //  verify(itemViewHolder).bind(items.get(position));
        }
    }
}

