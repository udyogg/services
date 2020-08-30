package com.udyogg.services.common.executer;



import com.udyogg.services.common.ReactiveContext.Contexts;
import com.udyogg.services.common.ReactiveContext.ReactiveContext;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * A custom scheduler for copying thread local context objects from
 * one thread to another thread using closure. This will be executed by Reactive Scheduler
 * during thread switching
 *
 * @author Ravi Singh
 * @created 19/04/2020 - 13:44
 * @project contra
 */
public final class TraceableScheduledExecuterService implements ScheduledExecutorService {



    private ScheduledExecutorService delegate;


    public TraceableScheduledExecuterService(ScheduledExecutorService delegate) {
        this.delegate = delegate;
    }

    private Runnable decorate(Runnable command) {



        Contexts.LocalContext current = Contexts.current();

        return () -> {
            ReactiveContext.instance().updateLocalContext(current);
            command.run();
        };
    }

    /* It's kind of a closure where one details are getting stored in one thread and then are getting used in another thread */
    private <V> Callable<V> decorate(Callable<V> command) {

        Contexts.LocalContext current = Contexts.current();


        return () -> {
            ReactiveContext.instance().updateLocalContext(current);
            return command.call();
        };
    }


    @Override
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return delegate.schedule(decorate(command), delay, unit);
    }


    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return delegate.schedule(decorate(callable), delay, unit);
    }


    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(
            Runnable command, long initialDelay, long period, TimeUnit unit) {
        return delegate.scheduleAtFixedRate(decorate(command), initialDelay, period, unit);
    }


    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(
            Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return delegate.scheduleWithFixedDelay(decorate(command), initialDelay, delay, unit);
    }


    @Override
    public void shutdown() {
        this.delegate.shutdown();
    }


    @Override
    public List<Runnable> shutdownNow() {
        return this.delegate.shutdownNow();
    }


    @Override
    public boolean isShutdown() {
        return this.delegate.isShutdown();
    }


    @Override
    public boolean isTerminated() {
        return this.delegate.isTerminated();
    }


    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return this.delegate.awaitTermination(timeout, unit);
    }


    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return this.delegate.submit(decorate(task));
    }


    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return this.delegate.submit(decorate(task), result);
    }


    @Override
    public Future<?> submit(Runnable task) {
        return this.delegate.submit(decorate(task));
    }


    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
            throws InterruptedException {
        return this.delegate.invokeAll(tasks);
    }


    @Override
    public <T> List<Future<T>> invokeAll(
            Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException {
        return this.delegate.invokeAll(
                tasks.stream().map(this::decorate).collect(Collectors.toList()), timeout, unit);
    }


    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks)
            throws InterruptedException, ExecutionException {
        return this.delegate.invokeAny(tasks.stream().map(this::decorate).collect(Collectors.toList()));
    }


    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        return this.delegate.invokeAny(
                tasks.stream().map(this::decorate).collect(Collectors.toList()), timeout, unit);
    }


    @Override
    public void execute(Runnable command) {
        this.delegate.execute(decorate(command));
    }
}
