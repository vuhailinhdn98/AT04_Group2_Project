package report;

import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.StepResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AllureListener implements StepLifecycleListener {

    protected final Logger log = LoggerFactory.getLogger(getClass());


    @Override
    public void beforeStepStart(StepResult result) {
        StepLifecycleListener.super.beforeStepStart(result);
        log.info(result.getName());
    }
}
