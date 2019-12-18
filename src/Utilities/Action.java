package Utilities;

public interface Action<T,U> {
	U execute(T t);
}
