export async function upload(formData: FormData) {
	const response = await fetch(process.env.SERVER_ADDRESS!, {
		method: "POST",
		body: formData
	})
	const data = await response.text()
	return data
}

export async function getImages() {
	const response = await fetch(process.env.SERVER_ADDRESS!)
	const data = await response.json()
	return data
}