package com.utn.mobile.mapasolidario;

public class UserProvider{

    public static User user;

    public static User get(){
        if(user == null)
            user = new User();
        return user;
    }

    public static User set(User _user){
        return user = _user;
    }
}