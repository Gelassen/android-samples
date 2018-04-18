package com.home.vkphotos.photos.preview;


import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

public class Repository {

    public interface Listener {
        void onSuccess(Response resp);
        void onError();
    }

    private Listener listener;

    public Repository(Listener listener) {
        this.listener = listener;
    }

    public void getData(String userId) {
        VKRequest request = new VKRequest("photos.getAll", VKParameters.from(VKApiConst.OWNER_ID, userId));
        request.executeWithListener(vkListener);
    }

    private VKRequest.VKRequestListener vkListener = new VKRequest.VKRequestListener() {
        @Override
        public void onComplete(VKResponse response) {
            super.onComplete(response);

            Response resp = new JsonToResponseConverter().convert(response.json);
            if (listener != null) listener.onSuccess(resp);
        }

        @Override
        public void onError(VKError error) {
            super.onError(error);
            if (listener != null) listener.onError();
        }
    };
}
