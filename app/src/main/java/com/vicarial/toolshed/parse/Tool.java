package com.vicarial.toolshed.parse;

/**
 * Created by jayl on 12/29/14.
 */
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

@ParseClassName("Tool")
public class Tool extends ParseObject {

    public Tool()
    {
        // A default constructor is required.
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) { put("name", name); }

    public String getDescription() {
        return getString("description");
    }

    public void setDescription(String desc) { put("description", desc); }

    public ParseUser getOwner() {
        return getParseUser("owner");
    }

    public void setOwner(ParseUser user) {
        put("owner", user);
    }

    public List<String> getTags() {
        return getList("tags");
    }

    public ParseFile getImage() {
        return getParseFile("image");
    }

    public void setImage(ParseFile img) {
        put("image", img);
    }

    public void addTag(String tagName) { add("tags", tagName);}

}
