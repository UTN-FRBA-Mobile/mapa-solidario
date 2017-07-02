/*
 *
 *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.utn.mobile.mapasolidario.login;

import com.utn.mobile.mapasolidario.User;

import org.json.JSONObject;

public interface LoginInteractor {

    interface OnGetUserFinishedListener {
        void onUserExists(User user);

        void onUserNotExists();
    }

    interface OnSaveUserFinishedListener {
        void onUserSaved(User user);

        void onUserNotSaved();
    }
    void getUser(String id, final OnGetUserFinishedListener listener);
    void saveUser(final OnSaveUserFinishedListener listener);

}
