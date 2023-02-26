package com.example.familymapclient.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.familymapclient.R;
import com.example.familymapclient.model.DataCache;
import com.example.familymapclient.model.SearchResult;
import com.example.shared.models.Event;
import com.example.shared.models.Person;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        SearchView searchBar = findViewById(R.id.search_bar);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchResult searchResult = new SearchResult(query);
                refreshAdapter(searchResult);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        List<Person> people = new ArrayList<>();
        List<Event> events = new ArrayList<>();

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(people, events);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void refreshAdapter(SearchResult searchResult) {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(searchResult.getPeople(), searchResult.getEvents());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent intent= new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

        private final List<Person> people;
        private final List<Event> events;

        RecyclerViewAdapter(List<Person> people, List<Event> events) {
            this.people = people;
            this.events = events;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;

            view = getLayoutInflater().inflate(R.layout.expandable_group_description, parent, false);

            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
            if(position < people.size()) {
                holder.bind(people.get(position));
            } else {
                holder.bind(events.get(position - people.size()));
            }
        }

        @Override
        public int getItemCount() {
            return people.size() + events.size();
        }
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView icon;
        private final TextView topText;
        private final TextView bottomText;

        private int viewType;
        private Person person;
        private Event event;

        RecyclerViewHolder(View view) {
            super(view);

            itemView.setOnClickListener(this);

            icon = itemView.findViewById(R.id.personGenderIcon);
            topText = itemView.findViewById(R.id.listItemTitle);
            bottomText = itemView.findViewById(R.id.listItemDescription);

        }

        private void bind(Person person) {
            this.person = person;
            Drawable genderIcon = new IconDrawable(SearchActivity.this, FontAwesomeIcons.fa_male).
                    colorRes(R.color.male_icon).sizeDp(30);
            icon.setImageDrawable(genderIcon);

            topText.setText(person.getFirstName() + " " + person.getLastName());
            viewType = 0;
        }

        private void bind(Event event) {
            this.event = event;
            Drawable genderIcon = new IconDrawable(SearchActivity.this, FontAwesomeIcons.fa_map_marker).
                    colorRes(R.color.black).sizeDp(30);
            icon.setImageDrawable(genderIcon);

            DataCache dataCache = DataCache.getInstance();
            topText.setText(event.getEventType() + ": " + event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ")");
            Person curr = dataCache.getPeople().get(event.getPersonID());
            bottomText.setText(curr.getFirstName() + " " + curr.getLastName());
            viewType = 1;
        }

        @Override
        public void onClick(View view) {
            DataCache dataCache = DataCache.getInstance();
            if(viewType == 0) {
                Intent intent = new Intent(SearchActivity.this, PersonActivity.class);
                intent.putExtra("personOfInterest", person.getPersonID());
                SearchActivity.this.startActivity(intent);
            } else {
                dataCache.setSelectedEventActivity(event);
                Intent intent = new Intent(SearchActivity.this, EventActivity.class);
                SearchActivity.this.startActivity(intent);
            }
        }
    }
}