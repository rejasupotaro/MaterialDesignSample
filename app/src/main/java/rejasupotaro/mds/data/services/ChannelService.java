package rejasupotaro.mds.data.services;

import java.util.ArrayList;
import java.util.List;

import rejasupotaro.mds.data.models.Channel;
import rx.Observable;

public class ChannelService {
    public Observable<List<Channel>> getList() {
        List<Channel> channels = new ArrayList<Channel>() {{
            add(Channel.dummy());
            add(Channel.dummy());
        }};
        return Observable.just(channels);
    }
}
