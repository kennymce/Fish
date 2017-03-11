
/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kennymce.games;

public class PlayerInfo {
    public enum Gender { MALE, FEMALE };
    public enum PlayerType {COMPUTER, REALPERSON};

    public static class Name {
        private String _first, _last;

        public String getFirst() { return _first; }
        public String getLast() { return _last; }

        public void setFirst(String s) { _first = s; }
        public void setLast(String s) { _last = s; }

        public String toString(){
            return  _first + " " + _last;
        }
    }

    private Gender _gender;
    private Name _name;
    private PlayerType _type;

    public PlayerType getType() { return _type; }
    public Name getName() { return _name; }
    public Gender getGender() { return _gender; }

    public void setName(Name n) { _name = n; }
    public void setGender(Gender g) { _gender = g; }
    public void setType(PlayerType _type) {this._type = _type; }
 }