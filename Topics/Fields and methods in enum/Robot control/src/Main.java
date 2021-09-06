class Move {
    public static void moveRobot(Robot robot, int toX, int toY) {
        int currentX = robot.getX();
        int currentY = robot.getY();

        if (currentX < toX) {
            do {
                robot.turnRight();
            } while (robot.getDirection() != Direction.RIGHT);
            for (int i = currentX; i < toX; i++) {
                robot.stepForward();
            }
        } else if (currentX > toX) {
            do {
                robot.turnLeft();
            } while (robot.getDirection() != Direction.LEFT);
            for (int i = toX; i < currentX; i++) {
                robot.stepForward();
            }
        }

        if (currentY < toY) {
            do {
                robot.turnRight();
            } while (robot.getDirection() != Direction.UP);
            for (int i = currentY; i < toY; i++) {
                robot.stepForward();
            }
        } else if (currentY > toY) {
            do {
                robot.turnLeft();
            } while (robot.getDirection() != Direction.DOWN);
            for (int i = toY; i < currentY; i++) {
                robot.stepForward();
            }
        }
    }
}

//Don't change code below

enum Direction {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Direction turnLeft() {
        switch (this) {
            case UP:
                return LEFT;
            case DOWN:
                return RIGHT;
            case LEFT:
                return DOWN;
            case RIGHT:
                return UP;
            default:
                throw new IllegalStateException();
        }
    }

    public Direction turnRight() {
        switch (this) {
            case UP:
                return RIGHT;
            case DOWN:
                return LEFT;
            case LEFT:
                return UP;
            case RIGHT:
                return DOWN;
            default:
                throw new IllegalStateException();
        }
    }

    public int dx() {
        return dx;
    }

    public int dy() {
        return dy;
    }
}

class Robot {
    private int x;
    private int y;
    private Direction direction;

    public Robot(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void turnLeft() {
        direction = direction.turnLeft();
    }

    public void turnRight() {
        direction = direction.turnRight();
    }

    public void stepForward() {
        x += direction.dx();
        y += direction.dy();
    }

    public Direction getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}