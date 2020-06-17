package com.example.android.roomwordssample;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.AlertDialog;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;


public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Word> mWords = Collections.emptyList(); // Cached copy of words

    public  View mView;
    private WordViewModel mViewModel;
    private MainActivity act;

    WordListAdapter(Context context, WordViewModel mViewModel, MainActivity activity) {
        mInflater = LayoutInflater.from(context);
        this.mViewModel = mViewModel;
        act = activity;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        mView= mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(mView);
    }

    private static final String action = "EDITAR";
    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        final Word current = mWords.get(position);
        holder.wordItemView.setText(String.format("%d-%s", current.getId(), current.getWord()));

        holder.wordItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.startIntent(action, current);
            }
        });

        holder.wordItemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mView.getContext());
                //set title and message
                builder.setTitle("Eliminar Palabra");
                builder.setMessage("Desea eliminar la palabra '"+current.getWord()+"'?");
                // Add the buttons
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        mViewModel.delete(current);
                        notifyDataSetChanged();
                        Toast.makeText(mView.getContext(),"Se elimino la palabra",Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        Toast.makeText(mView.getContext(),"No se eliminara la palabra",Toast.LENGTH_SHORT).show();
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });
    }

    void setWords(List<Word> words) {
        mWords = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mWords.size();
    }
}


