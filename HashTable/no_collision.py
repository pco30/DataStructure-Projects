class HashTable:
    def __init__(self):
        self.MAX = 100
        self.arr = [None for _ in range(self.MAX)]

    def get_hash(self, key):
        hash = 0
        for char in key:
            hash += ord(char)
        return hash % self.MAX

    def insert(self, key, value):
        index = self.get_hash(key)
        self.arr[index] = value

    def get(self, key):
        index = self.get_hash(key)
        return self.arr[index]

    def remove(self, key):
        index = self.get_hash(key)
        self.arr[index] = None

t = HashTable()
t.insert('march 6', 310)
t.insert('march 17', 420)
print(t.get('march 6'))
t.remove('march 6')
print(t.get('march 6'))