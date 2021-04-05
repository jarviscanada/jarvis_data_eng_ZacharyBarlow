package ca.jrvs.practice.challenges.tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class JBSTree<E> implements JTree<E> {

  private Comparator<E> comparator;
  private Node<E> root;
  private Integer size = 0;

  public JBSTree(Comparator<E> comparator) {
    this.comparator = comparator;
  }

  /**
   * Insert an object into the tree.
   *
   * @param object item to be inserted
   * @return inserted item
   * @throws IllegalArgumentException if the object already exists
   */
  @Override
  public E insert(E object) {

    updateNode(object, root,
        (node, obj) -> {node.setLeft(new Node<>(object, null)); return true;},
        (node, obj) -> {node.setRight(new Node<>(object, null)); return true;},
        (node, obj) -> {throw new IllegalArgumentException("Object exists in Tree");}
        );
    size++;
    return object;
  }

  /**
   * Search and return an object, return null if not found
   *
   * @param object to be found
   * @return the object if exists or null if not
   */
  @Override
  public E search(E object) {
    if (size == 0) {
      return null;
    }
    return updateNode(object, root,
        (node, obj) -> false,
        (node, obj) -> false,
        (node, obj) -> true) ? object : null;
  }

  /**
   * Remove an object from the tree.
   *
   * @param object to be removed
   * @return removed object
   * @throws IllegalArgumentException if the object not exists
   */
  @Override
  public E remove(E object) {
    if (size == 0) {
      return null;
    }
    updateNode(object, root,
        (node, obj) -> {throw new IllegalArgumentException("Object exists in Tree");},
        (node, obj) -> {throw new IllegalArgumentException("Object exists in Tree");},
        (node, obj) -> {
          // location is found
          // find left most node of right subtree
          // switch positions and then delete

          Node<E> parent = node.getParent();
          if (node.getLeft() != null) {
            Node<E> left = leftMostRightSubtree(node);
            left.getParent().setLeft(null); // remove left child for parent
            left.setLeft(node.getLeft()); // set the new parent to the left subtree of the node
            left.setRight(node.getRight()); // right subtree
            left.setParent(parent); // set its parent
          }
          if (comparator.compare(object, parent.getValue()) < 0) {
            parent.setLeft(node); //
          } else {
            parent.setRight(node);
          }
          size--;
          return true;
        }
        );
    return object;
  }

  /**
   * traverse the tree recursively
   *
   * @return all objects in pre-order
   */
  @Override
  public E[] preOrder() {
    List<E> arr = new ArrayList<>();
    Node<E> curr = root;
    if (curr == null) {
      return null;
    }
    Stack<Node<E>> s = new Stack<>();
    s.push(curr);
    while (!s.isEmpty()) {
      curr = s.pop();
      arr.add(curr.getValue());
      if (curr.getRight() != null) {
        s.push(curr.getRight());
      }
      if (curr.getLeft() != null) {
        s.push(curr.getLeft());
      }
    }

    return (E[]) arr.toArray();
  }

  /**
   * traverse the tree recursively
   *
   * @return all objects in-order
   */
  @Override
  public E[] inOrder() {
    List<E> arr = new ArrayList<>();
    Stack<Node<E>> s = new Stack<>();
    Node<E> curr = root;
    while (!s.isEmpty() || curr != null) {
      if (curr != null) {
        s.push(curr);
        curr = curr.getLeft();
      } else {
        curr = s.pop();
        arr.add(curr.getValue());
        curr = curr.getRight();
      }
    }
    return (E[]) arr.toArray();
  }

  /**
   * traverse the tree recursively
   *
   * @return all objects pre-order
   */
  @Override
  public E[] postOrder() {
    List<E> arr = new ArrayList<>();
    Stack<Node<E>> s = new Stack<>();
    Node<E> curr = root;
    Node<E> last = null;
    while (!s.isEmpty() || curr != null) {
      if (curr != null) {
        s.push(curr);
        curr = curr.getRight();
      } else {
        Node<E> peek = s.peek();
        if (peek.getRight() != null && !last.equals(peek.getRight())) {
          curr = peek.getRight();
        } else {
          arr.add(curr.getValue());
          last = s.pop();
        }
      }
    }
    return (E[]) arr.toArray();
  }

  /**
   * traverse through the tree and find out the tree height
   *
   * @return height
   * @throws NullPointerException if the BST is empty
   */
  @Override
  public int findHeight() {
    return findHeightHelper(root);
  }

  private int findHeightHelper(Node<E> node) {
    if (node == null) {
      return 0;
    }
    return findMax(findHeightHelper(node.getLeft()), findHeightHelper(node.getRight())) + 1;
  }

  private int findMax(int a, int b) {
    if (a >= b) {
      return a;
    }
    return b;
  }

  /**
   * INTERFACE
   * @param <E>
   */
  public interface UpdateFn<E> {
    Boolean update(Node<E> parent, E object);
  }

  public interface LeftSubTreeFn<E> extends UpdateFn<E> {}
  public interface RightSubTreeFn<E> extends UpdateFn<E> {}

  private Boolean updateNode(E object, Node<E> parent, LeftSubTreeFn left, RightSubTreeFn right, UpdateFn eq) {
    int s = comparator.compare(object, parent.getValue());
    if (s < 0) { // left
      if (parent.getLeft() == null) {
        return left.update(parent, object);
      } else {
        return updateNode(object, parent.getLeft(), left, right, eq);
      }
    } else if (s > 0) { // right
      if (parent.getRight() == null) {
        return right.update(parent, object);
      } else {
        return updateNode(object, parent.getRight(), left, right, eq);
      }
    } else {
      return eq.update(parent, object);
    }
  }

  public Node<E> leftMostRightSubtree(Node<E> curr) {
      while (curr.getLeft() != null) {
        curr = curr.getLeft();
      }
      return curr;
  }

  static final class Node<E> {

    E value;
    Node<E> left;
    Node<E> right;
    Node<E> parent;

    public Node(E value, Node<E> parent) {
      this.value = value;
      this.parent = parent;
    }

    public E getValue() {
      return value;
    }

    public void setValue(E value) {
      this.value = value;
    }

    public Node<E> getLeft() {
      return left;
    }

    public void setLeft(Node<E> left) {
      this.left = left;
    }

    public Node<E> getRight() {
      return right;
    }

    public void setRight(Node<E> right) {
      this.right = right;
    }

    public Node<E> getParent() {
      return parent;
    }

    public void setParent(Node<E> parent) {
      this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Node)) {
        return false;
      }
      Node<?> node = (Node<?>) o;
      return getValue().equals(node.getValue()) &&
          Objects.equals(getLeft(), node.getLeft()) &&
          Objects.equals(getRight(), node.getRight()) &&
          getParent().equals(node.getParent());
    }

    @Override
    public int hashCode() {
      return Objects.hash(getValue(), getLeft(), getRight(), getParent());
    }
  }
}
