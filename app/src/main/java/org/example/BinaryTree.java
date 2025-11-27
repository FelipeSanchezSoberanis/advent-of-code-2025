package org.example;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
public class BinaryTree<T> {
  private final T[] items;

  public Optional<Node<T>> getNodeAt(int index) {
    if (index >= items.length) return Optional.empty();
    return Optional.of(new Node<T>(index, items[index]));
  }

  public Optional<Node<T>> getRootNode() {
    return getNodeAt(0);
  }

  public Optional<Node<T>> getLeftNodeOf(Node<T> node) {
    return getNodeAt(2 * node.getIndex() + 1);
  }

  public Optional<Node<T>> getRightNodeOf(Node<T> node) {
    return getNodeAt(2 * node.getIndex() + 2);
  }

  public void bfs(Consumer<Node<T>> consumer) {
    Deque<Node<T>> queue = new ArrayDeque<>();
    getRootNode()
        .ifPresent(
            rootNode -> {
              queue.add(rootNode);
              while (!queue.isEmpty()) {
                Node<T> node = queue.removeFirst();
                getLeftNodeOf(node).ifPresent(queue::addLast);
                getRightNodeOf(node).ifPresent(queue::addLast);
                consumer.accept(node);
              }
            });
  }

  public void dfs(Consumer<Node<T>> consumer) {
    Deque<Node<T>> stack = new ArrayDeque<>();
    getRootNode()
        .ifPresent(
            rootNode -> {
              stack.add(rootNode);
              while (!stack.isEmpty()) {
                Node<T> node = stack.removeLast();
                getRightNodeOf(node).ifPresent(stack::addLast);
                getLeftNodeOf(node).ifPresent(stack::addLast);
                consumer.accept(node);
              }
            });
  }

  @AllArgsConstructor
  @Getter
  @ToString
  public static class Node<T> {
    private final int index;
    private final T value;
  }
}
