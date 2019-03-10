package ru.sergon.song.recycler;

import ru.sergon.song.models.SenderResponse.Post;

/**
 * Created by Sergey on 10/9/2017.
 */

public interface onItemClickListener {

    void onItemClick(Post post, int i);
}
