package nl.elastique.poetry.core.concurrent.callbacks;

import nl.elastique.poetry.core.annotations.Nullable;
import nl.elastique.poetry.core.concurrent.Callback;

/**
 * A class that implements Callback and provides a single onResult method that
 * is called both on onSuccess and onFailure cases.
 * When onSuccess(value) is called, then onResult(true, value, null) is called.
 * When onFailure(caught) is called, then onResult(false, null, caught) is called.
 * @param <T> the callback data type
 */
public abstract class UnifiedCallback<T> implements Callback<T>
{
    protected abstract void onResult(boolean success, T value, @Nullable Throwable caught);

    @Override
    public void onSuccess(T value)
    {
        onResult(true, value, null);
    }

    @Override
    public void onFailure(Throwable caught)
    {
        onResult(false, null, caught);
    }
}
