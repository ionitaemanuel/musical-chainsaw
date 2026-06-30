package betr.intern.chainsaw.repository;

// i don't really know if i need this anymore
// after we already have interface UserRepository
// i'll keep it for questions
public interface IRepository<T> {
    T add(T t);

    T get(T t);

    T remove(T t);

    T update(T t);
}
