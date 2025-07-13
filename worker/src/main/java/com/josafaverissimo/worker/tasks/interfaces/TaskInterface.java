package com.josafaverissimo.worker.tasks.interfaces;

@FunctionalInterface
public interface TaskInterface<T> {
  public void run(T data);
}
