import json, pyodbc

def lambda_handler(event, context):
    try:
        # Parámetros de conexión a la base de datos:
        driver = '{ODBC Driver 17 for SQL Server}'
        server = 'database-1.clu6gy7vcjed.us-east-1.rds.amazonaws.com'
        database = 'XDigital'
        username = 'admin'
        password = 'moviles2025.'

        # Stored procedure para obtener listado de productos:
        sp = 'sp_ListarProductos'


        with pyodbc.connect(f"Driver={driver};"
                            f"Server={server};"
                            f"Database={database};"
                            f"UID={username};"
                            f"PWD={password};") as conn:

            with conn.cursor() as cursor:
                cursor.execute(sp)
                columns = [col[0] for col in cursor.description]
                rows = cursor.fetchall()
                productos = [dict(zip(columns, row)) for row in rows]
                lista_productos = []

                for producto in productos:
                    dict_producto = {
                            "IdProducto": producto["idproducto"],
                            "Categoria": producto["categoria"],
                            "Marca": producto["marca"],
                            "Nombre": producto["nombre_producto"],
                            "Descripcion": producto["descripcion"],
                            "Precio": producto["precio"],
                            "Stock": producto["stock"],
                            "Imagen": producto["UrlImagen"]
                        }
                    lista_productos.append(dict_producto)

                return lista_productos
    except Exception as e:
        return {'Error': str(e)}
