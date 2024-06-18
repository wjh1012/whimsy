package day;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author jh.wang
 * @since 2024/5/30
 */
public class Day20240530 {

    static class AviTreeNode {
        Node root;

        public AviTreeNode(int value) {
            this.root = new Node(value);
        }

        AviTreeNode insert(int value) {
            final Node newNode = compareAndSet(this.root, value);
            checkAndSet(newNode);
            return this;
        }

        Node compareAndSet(Node root, int value) {
            Node newNode = null;
            if (value < root.value) {
                if (root.left != null) {
                    newNode = compareAndSet(root.left, value);
                } else {
                    root.left = new Node(value, root);
                    return root.left;
                }
            } else if (value > root.value) {
                if (root.right != null) {
                    newNode = compareAndSet(root.right, value);
                } else {
                    root.right = new Node(value, root);
                    return root.right;
                }
            } else {
                System.out.println(root + "无效数据");
            }
            return newNode;
        }

        void checkAndSet(Node newNode) {
            if (newNode.root == null || newNode.root.root == null) {
                return;
            }

            if (Math.abs(newNode.height - newNode.root.root.height) <= 1) {
                return;
            }

            // 失衡节点
            final int unbalancedNodeVal = (newNode.root.root.left == null ? 1 : newNode.root.root.left.maxHeight()) - (newNode.root.root.right == null ? 1 : newNode.root.root.right.maxHeight());
            // 失衡子节点
            final int unbalancedChildNodeVal = newNode.root.leftHeight(0) - newNode.root.rightHeight(0);

            System.out.println("失衡: " + newNode + " " + unbalancedNodeVal + " " + unbalancedChildNodeVal);

            // LR
            if (unbalancedNodeVal == 2 && unbalancedChildNodeVal == -1) {
                // 左旋子节点
                newNode.left = newNode.root;
                newNode.root.right = null;
                Node root = newNode.left.root;
                newNode.left.root = newNode;
                newNode.root = root;

                // todo 未完
            }
        }


        @Override
        public String toString() {
            return root.toString(root);
        }
    }

    static class Node {
        Node root;

        Node left;
        Node right;

        int value;
        int height;

        public Node(int value) {
            this.value = value;
            this.height = 1;
        }

        private Node(int value, Node root) {
            this.value = value;
            this.root = root;
            this.height = root.height + 1;
        }

        int maxHeight() {
            return Math.max(leftMaxHeight(), rightMaxHeight());
        }

        int leftMaxHeight() {
            return left != null ? left.leftMaxHeight() : height;
        }

        int rightMaxHeight() {
            return right != null ? right.rightMaxHeight() : height;
        }

        public int leftHeight(int height) {
            return left != null ? left.leftHeight(height + 1) : height;
        }

        public int rightHeight(int height) {
            return right != null ? right.rightHeight(height + 1) : height;
        }

        @Override
        public String toString() {
            return "" + value;
        }

        public String toString(Node root) {
            final int maxHeight = maxHeight(root);

            StringBuilder sb = new StringBuilder();
            sb.append(" ".repeat(maxHeight));
            sb.append(root.value);

            one(maxHeight, sb);

            return sb.toString();
        }

        private void one(int maxHeight, StringBuilder sb) {
            for (int i = 2; i <= maxHeight; i++) {
                List<Node> nodes = new ArrayList<>();
                heightNode(nodes, i);

                if (!nodes.isEmpty()) {
                    sb.append("\r\n");
                }

                Collections.reverse(nodes);

                for (Node node : nodes) {
                    sb.append(" ")
                            .append(node.value)
                            .append("(")
                            .append((node.root.left != null && node.root.left.value == node.value) ? "l" : "r")
                            .append(" ")
                            .append(node.root.value)
                            .append(")");
                }
            }
        }

        public void heightNode(List<Node> nodes, int height) {
            if (this.height == height) {
                nodes.add(this);
            }

            if (right != null) {
                right.heightNode(nodes, height);
            }

            if (left != null) {
                left.heightNode(nodes, height);
            }
        }


        public int maxHeight(Node node) {
            // left不为空
            if (node.left != null && node.right == null) {
                return maxHeight(node.left);
            }

            // right不为空
            if (node.left == null && node.right != null) {
                return maxHeight(node.right);
            }

            // left right 都为空
            if (node.left == null && node.right == null) {
                return node.height;
            }

            // left right 都不为空
            return Math.max(maxHeight(node.left), maxHeight(node.right));
        }


    }

    public static void main(String[] args) {
        final AviTreeNode aviTreeNode = new AviTreeNode(20);

        aviTreeNode
                .insert(12)
                .insert(17)
        // .insert(23)
        // .insert(19)
        // .insert(29)
        // .insert(21)
        // .insert(28)
        // .insert(27)
        // .insert(26)
        // .insert(7)
        ;

        System.out.println(aviTreeNode);
    }


}
