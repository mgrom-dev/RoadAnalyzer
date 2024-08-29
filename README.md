# RoadAnalyzer
application for cars
## Tables db

Unique id numbering of all tables with UUID.randomUUID()

**Part_and_Service**
| id | description                                     | type |
|----|-------------------------------------------------|------|
| 1  | Автобензин АИ-95                                | 1    |
| 2  | Мойка машины                                    | 2    |
| 3  | Тормозные колодки передние комплект TRW GDB2108 | 3    |

**Expense_Type**
| id | description |
|----|-------------|
| 1  | топливо     |
| 2  | услуги      |
| 3  | запчасти    |

**Spending**
| id | date       | Part_and_Service_ID | description     | count | amount  |
|----|------------|---------------------|-----------------|-------|---------|
| 1  | 16.06.2024 | 1                   |                 | 30    | 1615.15 |
| 2  | 20.06.2024 | 2                   | кузов + коврики | 1     | 100     |
| 3  | 25.06.2024 | 3                   |                 | 1     | 2400    |


**Fuel**
| id | Spending_ID | odometer | description |
|----|-------------|----------|-------------|
| 1  | 1           | 137014   |             |
| 2  | 8           |          | трасса      |
| 3  | 30          | 138775   |             |
