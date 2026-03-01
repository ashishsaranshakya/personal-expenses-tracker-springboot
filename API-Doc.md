# Expense Tracker API Documentation

### Authentication

- **Google Login**
  - Endpoint: `GET /auth/google`
  - Description: Redirects the user to Google for authentication.

- **Google Callback**
  - Endpoint: `GET /auth/google/callback`
  - Description: Handles the callback from Google after authentication.

- **Logout User**
  - Endpoint: `GET /auth/logout`
  - Description: Logs out the authenticated user.

### Expenses

- **Create Expense**
  - Endpoint: `POST /expenses`
  - Description: Creates a new expense entry. Requires authentication and valid expense data.
  - Body: JSON object containing expense data.
    - `title`: Title of the expense.
    - `amount`: Amount of the expense.
    - `category`: Category of the expense.
    - `date`: Date of the expense.

- **Get Expenses**
  - Endpoint: `GET /expenses`
  - Description: Retrieves all expenses for the authenticated user.

- **Get Expense By ID**
  - Endpoint: `GET /expenses/:id`
  - Description: Retrieves a specific expense by its ID. Requires authentication.
  - Parameters:
    - `id`: ID of the expense to retrieve.

- **Update Expense**
  - Endpoint: `PUT /expenses/:id`
  - Description: Updates a specific expense by its ID. Requires authentication and valid expense data.
  - Parameters:
    - `id`: ID of the expense to update.
  - Body: JSON object containing updated expense data.
    - `title`: Updated title of the expense.
    - `amount`: Updated amount of the expense.
    - `category`: Updated category of the expense.
    - `date`: Updated date of the expense.

- **Delete Expense**
  - Endpoint: `DELETE /expenses/:id`
  - Description: Deletes a specific expense by its ID. Requires authentication.
  - Parameters:
    - `id`: ID of the expense to delete.

### Incomes

- **Create Income**
  - Endpoint: `POST /incomes`
  - Description: Creates a new income entry. Requires authentication and valid income data.
  - Body: JSON object containing income data.
    - `title`: Title of the income.
    - `amount`: Amount of the income.
    - `category`: Category of the income.
    - `date`: Date of the income.

- **Get Incomes**
  - Endpoint: `GET /incomes`
  - Description: Retrieves all incomes for the authenticated user.

- **Get Income By ID**
  - Endpoint: `GET /incomes/:id`
  - Description: Retrieves a specific income by its ID. Requires authentication.
  - Parameters:
    - `id`: ID of the income to retrieve.

- **Update Income**
  - Endpoint: `PUT /incomes/:id`
  - Description: Updates a specific income by its ID. Requires authentication and valid income data.
  - Parameters:
    - `id`: ID of the income to update.
  - Body: JSON object containing updated income data.
    - `title`: Updated title of the income.
    - `amount`: Updated amount of the income.
    - `category`: Updated category of the income.
    - `date`: Updated date of the income.

- **Delete Income**
  - Endpoint: `DELETE /incomes/:id`
  - Description: Deletes a specific income by its ID. Requires authentication.
  - Parameters:
    - `id`: ID of the income to delete.

### User

- **Get Profile**
  - Endpoint: `GET /user`
  - Description: Retrieves the profile of the authenticated user.

- **Update Balance**
  - Endpoint: `PUT /user`
  - Description: Updates the balance of the authenticated user. Requires authentication and a valid balance value.
  - Body: JSON object containing updated balance data.
    - `balance`: Updated balance of the user.

- **Get Categories**
  - Endpoint: `GET /user/categories`
  - Description: Retrieves the income and expense categories for the authenticated user.

- **Add Income Category**
  - Endpoint: `POST /user/categories/income`
  - Description: Adds a new income category. Requires authentication and a valid category name.
  - Body: JSON object containing new income category data.
    - `name`: Name of the new income category.

- **Add Expense Category**
  - Endpoint: `POST /user/categories/expense`
  - Description: Adds a new expense category. Requires authentication and a valid category name.
  - Body: JSON object containing new expense category data.
    - `name`: Name of the new expense category.

- **Delete Income Category**
  - Endpoint: `DELETE /user/categories/income/:id`
  - Description: Deletes a specific income category by its ID. Requires authentication.
  - Parameters:
    - `id`: ID of the income category to delete.

- **Delete Expense Category**
  - Endpoint: `DELETE /user/categories/expense/:id`
  - Description: Deletes a specific expense category by its ID. Requires authentication.
  - Parameters:
    - `id`: ID of the expense category to delete.
