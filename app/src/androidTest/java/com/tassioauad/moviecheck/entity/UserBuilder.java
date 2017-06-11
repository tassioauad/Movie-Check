package com.tassioauad.moviecheck.entity;

import com.tassioauad.moviecheck.model.entity.User;

public class UserBuilder {

    private static final Long ID_VALID = 75l;
    private static final String GOOGLEID_VALID = "271110";
    private static final String NAME_VALID = "John Black";
    private static final String PHOTOURL_VALID = "/5N20rQURev5CNDcMjHVUZhpoCNC.jpg";
    private static final String EMAIL_VALID = "johnblack@gmail.com";

    private Long id;
    private String googleId;
    private String name;
    private String email;
    private String photoUrl;

    private UserBuilder() {}

    public static UserBuilder aUser() {
        return new UserBuilder().withId(ID_VALID).withGoogleId(GOOGLEID_VALID).withName(NAME_VALID)
                .withPhotoUrl(PHOTOURL_VALID).withEmail(EMAIL_VALID);
    }

    public UserBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder withGoogleId(String googleId) {
        this.googleId = googleId;
        return this;
    }

    public UserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public User build() {
        return new User(googleId, name, email, photoUrl);
    }
}
