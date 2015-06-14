package rejasupotaro.mds.view;

import android.app.Activity;
import android.content.Intent;

import rejasupotaro.mds.R;

public enum Transition {
    UNDEFINED(0) {
        @Override
        public void overrideOpenTransition(Activity activity) {
            // default
        }

        @Override
        public void overrideCloseTransition(Activity activity) {
            // default
        }
    },
    FADE_IN(1) {
        @Override
        public void overrideOpenTransition(Activity activity) {
            activity.overridePendingTransition(R.anim.transition_fade_in, R.anim.transition_fade_out);
        }

        @Override
        public void overrideCloseTransition(Activity activity) {
            activity.overridePendingTransition(R.anim.transition_fade_in, R.anim.transition_fade_out);
        }
    },
    PUSH_RIGHT_TO_LEFT(2) {
        @Override
        public void overrideOpenTransition(Activity activity) {
            activity.overridePendingTransition(R.anim.transition_push_left_enter, R.anim.transition_push_left_exit);
        }

        @Override
        public void overrideCloseTransition(Activity activity) {
            activity.overridePendingTransition(R.anim.transition_push_right_enter, R.anim.transition_push_right_exit);
        }
    },
    PUSH_UP(3) {
        @Override
        public void overrideOpenTransition(Activity activity) {
            activity.overridePendingTransition(R.anim.transition_fade_in, R.anim.transition_push_up_exit);
        }

        @Override
        public void overrideCloseTransition(Activity activity) {
            activity.overridePendingTransition(R.anim.transition_push_down_enter, R.anim.transition_fade_out);
        }
    };

    private static final String TRANSITION_ID = "extra_transition_id";

    private int id;

    Transition(int id) {
        this.id = id;
    }

    private static Transition fromId(int id) {
        for (Transition transition : values()) {
            if (id == transition.id) {
                return transition;
            }
        }
        return Transition.UNDEFINED;
    }

    public static void putTransition(Intent intent, Transition transition) {
        intent.putExtra(TRANSITION_ID, transition.getId());
    }

    public static Transition getTransition(Intent intent) {
        if (intent == null || intent.getExtras() == null) {
            return Transition.UNDEFINED;
        }

        return Transition.fromId(intent.getIntExtra(TRANSITION_ID, 0));
    }

    public abstract void overrideOpenTransition(Activity activity);

    public abstract void overrideCloseTransition(Activity activity);

    public int getId() {
        return id;
    }
}
