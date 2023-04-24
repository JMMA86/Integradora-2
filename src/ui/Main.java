package ui;

import model.Controller;

import java.util.Scanner;

public class Main {
    private Scanner sc;
    private Controller controller;

    public Main() {
        this.sc = new Scanner(System.in);
        this.controller = new Controller();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.showMenu();
    }

    public void showMenu() {

    }
}